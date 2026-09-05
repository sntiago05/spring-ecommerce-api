package com.sntiago05.ecommerceapi.checkout;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.exception.CartItemsillegalegalStateException;
import com.sntiago05.ecommerceapi.cart.service.CartService;
import com.sntiago05.ecommerceapi.order.dto.OrderResponse;
import com.sntiago05.ecommerceapi.order.entity.Order;
import com.sntiago05.ecommerceapi.order.entity.OrderItem;
import com.sntiago05.ecommerceapi.order.event.OrderEvent;
import com.sntiago05.ecommerceapi.order.event.OrderItemEvent;
import com.sntiago05.ecommerceapi.order.service.OrderService;
import com.sntiago05.ecommerceapi.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CheckOutService {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;
    
    /**
     * Processes the checkout operation for the given cart by converting its items into an order.
     * The method verifies that the cart contains items, subtracts the item quantities from their respective stock,
     * creates an order with the items, clears the cart, publishes an event, and returns the created order response.
     * @param cartId the unique identifier of the cart to be checked out
     * @return the {@code OrderResponse} containing the details of the processed items
     * @throws CartItemsillegalegalStateException if the cart does not have any items to process
     */
    @Transactional
    public OrderResponse processCheckOut(Long cartId) {
        Cart cart = cartService.getCartByIdWithItems(cartId);
        if (cart.getItems().isEmpty()) throw new CartItemsillegalegalStateException();
        Order order = new Order();
        order.setItems(new ArrayList<>());
        cart.getItems().forEach(item -> {
            productService.decreaseStock(item.getProduct().getId(), item.getQuantity());
            order.getItems().add(
                    OrderItem.builder()
                            .product(item.getProduct())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getProduct().getPrice())
                            .subtotal(item.calculateSubTotal())
                            .order(order)
                            .build()
            );

        });
        order.setUser(cart.getUser());
        order.setTotal(cart.calculateTotal());
        Order newOrder = orderService.saveOrder(order);
        cart.getItems().clear();

        eventPublisher.publishEvent(createEvent(newOrder, cart));
        return OrderResponse.fromEntity(newOrder);
    }

    private @NonNull OrderEvent createEvent(Order newOrder, Cart cart) {
        return new OrderEvent(newOrder.getId(), cart.getUser().getUsername(), cart.getUser().getEmail(), newOrder.getItems().stream().map(item -> new OrderItemEvent(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
        )).toList(), newOrder.getTotal());
    }
}

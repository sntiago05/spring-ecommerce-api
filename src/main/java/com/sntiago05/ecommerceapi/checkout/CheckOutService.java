package com.sntiago05.ecommerceapi.checkout;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.exception.CartItemsillegalegalStateException;
import com.sntiago05.ecommerceapi.cart.service.CartService;
import com.sntiago05.ecommerceapi.config.CurrentUserService;
import com.sntiago05.ecommerceapi.order.dto.OrderResponse;
import com.sntiago05.ecommerceapi.order.entity.Order;
import com.sntiago05.ecommerceapi.order.entity.OrderItem;
import com.sntiago05.ecommerceapi.order.event.OrderEvent;
import com.sntiago05.ecommerceapi.order.event.OrderItemEvent;
import com.sntiago05.ecommerceapi.order.service.OrderService;
import com.sntiago05.ecommerceapi.product.exceptions.ProductNotFoundException;
import com.sntiago05.ecommerceapi.product.exceptions.ProductOutOfStockException;
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
     * Processes the checkout for the currently authenticated user by creating an order from the user's cart.
     * The method performs the following operations:
     * - Retrieves the cart associated with the authenticated user's email.
     * - Validates the cart to ensure it contains at least one item.
     * - Decreases the stock of each product in the cart.
     * - Creates an order with the items from the cart, including their quantities, prices, and subtotals.
     * - Calculates the total price for the order.
     * - Saves the order, clears the cart, and publishes an event for the created order.
     *
     * @return an {@link OrderResponse} object containing details of the newly created order,
     *         including its ID, email associated with the order, total amount, and details of the items in the order.
     * @throws CartItemsillegalegalStateException if the cart is empty.
     * @throws ProductNotFoundException if a product in the cart is not found in the inventory.
     * @throws ProductOutOfStockException if a product in the cart does not have sufficient stock.
     */
    @Transactional
    public OrderResponse processCheckOut() {
        Cart cart = cartService.getCartEntityWithItemsByEmail();
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

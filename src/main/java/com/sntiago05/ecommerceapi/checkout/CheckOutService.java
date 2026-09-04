package com.sntiago05.ecommerceapi.checkout;

import com.sntiago05.ecommerceapi.cart.entity.Cart;
import com.sntiago05.ecommerceapi.cart.exception.CartItemsIlegalState;
import com.sntiago05.ecommerceapi.cart.service.CartService;
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

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CheckOutService {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Order processCheckOut(Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart.getItems().isEmpty()) throw new CartItemsIlegalState();
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

        eventPublisher.publishEvent(createEvent(newOrder, cart, order));
        return newOrder;
    }

    private @NonNull OrderEvent createEvent(Order newOrder, Cart cart, Order order) {
        return new OrderEvent(newOrder.getId(), cart.getUser().getUsername(), cart.getUser().getEmail(), newOrder.getItems().stream().map(item -> new OrderItemEvent(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
        )).toList(), order.getTotal());
    }
}

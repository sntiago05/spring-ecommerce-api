package com.sntiago05.ecommerceapi.order.dto;

import com.sntiago05.ecommerceapi.order.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id, String email, BigDecimal total, List<OrderItemResponse> items) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(order.getId(), order.getUser().getEmail(), order.getTotal(),
                order.getItems().stream().map(OrderItemResponse::fromEntity).toList());
    }
}

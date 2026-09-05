package com.sntiago05.ecommerceapi.order.dto;

import com.sntiago05.ecommerceapi.order.entity.Order;
import com.sntiago05.ecommerceapi.order.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtoTotal

) {
    public static OrderItemResponse fromEntity(OrderItem item) {
        return new OrderItemResponse(item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal());
    }
}

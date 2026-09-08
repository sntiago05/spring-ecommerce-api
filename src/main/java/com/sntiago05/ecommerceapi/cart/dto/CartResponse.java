package com.sntiago05.ecommerceapi.cart.dto;

import com.sntiago05.ecommerceapi.cart.entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> items,
        BigDecimal total
) {
    public static CartResponse fromEntity(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getItems().stream().map(CartItemResponse::fromEntity).toList(),
                cart.calculateTotal()
        );
    }
}

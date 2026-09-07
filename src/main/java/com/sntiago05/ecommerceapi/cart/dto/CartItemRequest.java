package com.sntiago05.ecommerceapi.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(
        @Positive
        @NotNull
        Long productId,
        @Positive
        @NotNull
        Integer quantity
) {
}

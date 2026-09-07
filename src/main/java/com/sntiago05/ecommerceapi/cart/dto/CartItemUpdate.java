package com.sntiago05.ecommerceapi.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemUpdate(
        @NotNull
        @Positive
        Integer quantity) {
}

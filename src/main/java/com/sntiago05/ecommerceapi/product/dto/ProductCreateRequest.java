package com.sntiago05.ecommerceapi.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank
        @Size(min = 8, max = 50)
        String name,
        @NotBlank
        @Size(max = 250)
        String description,
        @NotNull
        @DecimalMin("0.10")
        @Digits(integer = 8,fraction = 2)
        BigDecimal price,
        @NotNull
        @PositiveOrZero
        Integer stock

) {
}

package com.sntiago05.ecommerceapi.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @Size( max = 50)
        String name,
        @Size(max = 250)
        String description,
        @DecimalMin("0.10")
        @Digits(integer = 8, fraction = 2)
        BigDecimal price
) {
}

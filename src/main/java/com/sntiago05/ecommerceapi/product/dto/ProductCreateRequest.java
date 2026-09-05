package com.sntiago05.ecommerceapi.product.dto;

import com.sntiago05.ecommerceapi.product.entity.Product;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank
        @Size( max = 50)
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
        public static Product toEntity(ProductCreateRequest request) {
                return Product.builder().name(request.name())
                        .description(request.description())
                        .price(request.price())
                        .stock(request.stock())
                        .build();
        }
}

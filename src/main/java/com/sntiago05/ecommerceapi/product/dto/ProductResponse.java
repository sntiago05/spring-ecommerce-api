package com.sntiago05.ecommerceapi.product.dto;

import com.sntiago05.ecommerceapi.product.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, Integer Stock) {
    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }
}

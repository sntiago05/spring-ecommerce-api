package com.sntiago05.ecommerceapi.product.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("product with id " + id + "not found");
    }
}

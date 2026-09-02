package com.sntiago05.ecommerceapi.product.exceptions;

public class ProductConflictException extends RuntimeException {
    public ProductConflictException() {
        super("product already exists");
    }
}

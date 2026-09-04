package com.sntiago05.ecommerceapi.cart.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super("cart not found with id: " + id);
    }
}

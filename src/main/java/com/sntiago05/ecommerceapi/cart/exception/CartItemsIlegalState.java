package com.sntiago05.ecommerceapi.cart.exception;

public class CartItemsIlegalState extends RuntimeException {
    public CartItemsIlegalState() {
        super("cart is empty");
    }
}

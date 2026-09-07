package com.sntiago05.ecommerceapi.cart.exception;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CartItemNotFoundException extends BusinessException {

    public CartItemNotFoundException() {
        super("Cart Item not found", HttpStatus.NOT_FOUND);
    }
}

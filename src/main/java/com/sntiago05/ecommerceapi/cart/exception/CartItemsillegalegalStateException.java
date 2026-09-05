package com.sntiago05.ecommerceapi.cart.exception;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CartItemsillegalegalStateException extends BusinessException {
    public CartItemsillegalegalStateException() {
        super("cart is empty", HttpStatus.BAD_REQUEST);
    }
}

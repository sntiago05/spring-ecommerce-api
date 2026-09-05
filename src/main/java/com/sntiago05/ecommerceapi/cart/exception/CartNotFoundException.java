package com.sntiago05.ecommerceapi.cart.exception;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends BusinessException {
    public CartNotFoundException() {
        super("cart not found " , HttpStatus.NOT_FOUND);
    }
}

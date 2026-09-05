package com.sntiago05.ecommerceapi.product.exceptions;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProductConflictException extends BusinessException {
    public ProductConflictException() {
        super("product already exists", HttpStatus.CONFLICT);
    }
}

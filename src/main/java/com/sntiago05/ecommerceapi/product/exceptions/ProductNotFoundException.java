package com.sntiago05.ecommerceapi.product.exceptions;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(Long id) {
        super("product with id " + id + " not found", HttpStatus.NOT_FOUND);
    }
}

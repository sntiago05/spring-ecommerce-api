package com.sntiago05.ecommerceapi.product.exceptions;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProductOutOfStockException extends BusinessException {
    public ProductOutOfStockException(String name) {
        super("product "+name+" is out of stock", HttpStatus.CONFLICT);
    }
}

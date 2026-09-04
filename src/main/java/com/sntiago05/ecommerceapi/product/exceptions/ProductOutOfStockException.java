package com.sntiago05.ecommerceapi.product.exceptions;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String name) {
        super("product "+name+" is out of stock");
    }
}

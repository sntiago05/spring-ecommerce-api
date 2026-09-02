package com.sntiago05.ecommerceapi.exception;

import com.sntiago05.ecommerceapi.product.exceptions.ProductConflictException;
import com.sntiago05.ecommerceapi.user.exceptions.UserEmailConflictException;
import com.sntiago05.ecommerceapi.user.exceptions.UserInvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerExeception {
    @ExceptionHandler(UserEmailConflictException.class)
    public ResponseEntity<String> handleUserEmailConflictException(UserEmailConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserInvalidCredentialsException.class)
    public ResponseEntity<String> handleUserInvalidCredentialsException(UserInvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(ProductConflictException.class)
    public ResponseEntity<String> handleProductConflictException(ProductConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

package com.sntiago05.ecommerceapi.user.exceptions;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserEmailConflictException extends BusinessException {
    public UserEmailConflictException() {
        super("Email conflict", HttpStatus.CONFLICT);
    }
}

package com.sntiago05.ecommerceapi.user.exceptions;

import com.sntiago05.ecommerceapi.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserInvalidCredentialsException extends BusinessException {
    public UserInvalidCredentialsException() {
        super("User Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
}

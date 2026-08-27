package com.sntiago05.ecommerceapi.user.exceptions;

public class UserInvalidCredentialsException extends RuntimeException {
    public UserInvalidCredentialsException() {
        super("User Invalid Credentials");
    }
}

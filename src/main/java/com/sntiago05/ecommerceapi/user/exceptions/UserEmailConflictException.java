package com.sntiago05.ecommerceapi.user.exceptions;

public class UserEmailConflictException extends RuntimeException {
    public UserEmailConflictException()
    {
        super("Email conflict");
    }
}

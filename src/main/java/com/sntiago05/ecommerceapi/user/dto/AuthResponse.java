package com.sntiago05.ecommerceapi.user.dto;

public record AuthResponse(String token) {
    public  static AuthResponse fromString(String token) {
        return new AuthResponse(token);
    }
}

package com.sntiago05.ecommerceapi.user.dto;

import com.sntiago05.ecommerceapi.user.entity.User;

public record UserResponse(String userName, String email) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getUsername(), user.getEmail());
    }
}

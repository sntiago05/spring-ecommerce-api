package com.sntiago05.ecommerceapi.user.controller;

import com.sntiago05.ecommerceapi.user.dto.AuthResponse;
import com.sntiago05.ecommerceapi.user.dto.LoginRequest;
import com.sntiago05.ecommerceapi.user.dto.RegisterRequest;
import com.sntiago05.ecommerceapi.user.dto.UserResponse;
import com.sntiago05.ecommerceapi.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse response = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

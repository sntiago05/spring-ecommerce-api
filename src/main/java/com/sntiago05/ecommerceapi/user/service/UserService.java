package com.sntiago05.ecommerceapi.user.service;

import com.sntiago05.ecommerceapi.config.JwtService;
import com.sntiago05.ecommerceapi.user.entity.User;
import com.sntiago05.ecommerceapi.user.dto.LoginRequest;
import com.sntiago05.ecommerceapi.user.dto.RegisterRequest;
import com.sntiago05.ecommerceapi.user.repository.UserRepository;
import com.sntiago05.ecommerceapi.user.entity.UserRole;
import com.sntiago05.ecommerceapi.user.exceptions.UserEmailConflictException;
import com.sntiago05.ecommerceapi.user.exceptions.UserInvalidCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public String login(LoginRequest loginRequest) {
        User user = repo.findByEmail(loginRequest.email()).orElseThrow(UserInvalidCredentialsException::new);
        if (!encoder.matches(loginRequest.password(), user.getPassword()))
            throw new UserInvalidCredentialsException();
        return jwtService.generateToken(user.getEmail(), user.getRole());
    }

    public User register(RegisterRequest registerRequest) {
        if (repo.existsByEmail(registerRequest.email())) throw new UserEmailConflictException();
        User user = User.builder()
                .username(registerRequest.userName())
                .email(registerRequest.email())
                .password(encoder.encode(registerRequest.password()))
                .role(UserRole.CUSTOMER)
                .build();
        return repo.save(user);
    }
}

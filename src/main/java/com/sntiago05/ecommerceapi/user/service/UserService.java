package com.sntiago05.ecommerceapi.user.service;

import com.sntiago05.ecommerceapi.cart.service.CartService;
import com.sntiago05.ecommerceapi.config.JwtService;
import com.sntiago05.ecommerceapi.user.dto.AuthResponse;
import com.sntiago05.ecommerceapi.user.dto.UserResponse;
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
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final CartService cartService;
    
    /**
     * Authenticates a user based on the provided login request.
     * This method verifies the user's credentials and returns a JWT token
     * if the credentials are valid.
     *
     * @param loginRequest the login request containing the user's email and password.
     *                     The email must be a valid format and the password should
     *                     meet the required criteria.
     * @return a AuthResponse containing the JWT token string representing the authenticated user.
     * @throws UserInvalidCredentialsException if the user's email or password
     *         is invalid or does not match the records.
     */
    public AuthResponse login(LoginRequest loginRequest) {
        User user = repo.findByEmail(loginRequest.email()).orElseThrow(UserInvalidCredentialsException::new);
        if (!encoder.matches(loginRequest.password(), user.getPassword()))
            throw new UserInvalidCredentialsException();
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return AuthResponse.fromString(token);
    }
    
    /**
     * Registers a new user with the provided details, initializes a cart for the user,
     * and saves the user to the repository.
     *
     * @param registerRequest the request object containing the user's registration details,
     *                        which includes username, email, and password.
     * @return the {@link UserResponse} DTO representing the newly registered user.
     * @throws UserEmailConflictException if a user with the given email already exists in the repository.
     */
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        if (repo.existsByEmail(registerRequest.email())) throw new UserEmailConflictException();
        User user = User.builder()
                .username(registerRequest.userName())
                .email(registerRequest.email())
                .password(encoder.encode(registerRequest.password()))
                .role(UserRole.CUSTOMER)
                .build();
        User savedUser = repo.save(user);
        cartService.initCartToUser(savedUser);
        return UserResponse.fromEntity(savedUser);
    }
}

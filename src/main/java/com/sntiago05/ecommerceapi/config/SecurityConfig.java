package com.sntiago05.ecommerceapi.config;

import com.sntiago05.ecommerceapi.filters.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Configures the security filter chain for the application.
     *
     * This method sets up various security configurations, including:
     * - Disabling CSRF protection
     * - Using stateless session management
     * - Defining request authorization rules based on request matchers
     * - Adding a custom JWT filter before the {@link UsernamePasswordAuthenticationFilter}
     *
     * @param http the {@link HttpSecurity} object used to define security configurations
     * @return the configured {@link SecurityFilterChain} instance
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a ->
                        a.requestMatchers("/auth/**").permitAll()
                                .requestMatchers(GET, "/products/**").authenticated()
                                .requestMatchers(POST, "/products/**").hasRole("ADMIN")
                                .requestMatchers(PUT, "/products/**").hasRole("ADMIN")
                                .requestMatchers(DELETE, "/products/**").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

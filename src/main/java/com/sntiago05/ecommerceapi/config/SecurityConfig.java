package com.sntiago05.ecommerceapi.config;

import com.sntiago05.ecommerceapi.filters.JwtFilter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
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
     * <p>
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, exception) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, exception) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN)
                        )
                )
                .authorizeHttpRequests(a ->
                        a.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/cart/**").authenticated()
                                .requestMatchers("/checkout/**").authenticated()
                                .requestMatchers("/orders/**").authenticated()
                                .requestMatchers(GET, "/products/**").authenticated()
                                .requestMatchers(POST, "/products/**").hasRole("ADMIN")
                                .requestMatchers(PATCH, "/products/**").hasRole("ADMIN")
                                .requestMatchers(DELETE, "/products/**").hasRole("ADMIN")
                                .anyRequest().denyAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.sntiago05.ecommerceapi.config;


import com.sntiago05.ecommerceapi.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long exp;


    private SecretKey getSinginKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String userName, UserRole role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + exp);
        return Jwts.builder()
                .subject(userName)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSinginKey())
                .compact();
    }

    private JwtParser buildParser() {
        return Jwts.parser().verifyWith(getSinginKey()).build();
    }

    public JwtClaim extractClaims(String token) {
        Claims claims = buildParser().parseSignedClaims(token).getPayload();
        return new JwtClaim(claims.getSubject(), claims.get("role", String.class));
    }

}

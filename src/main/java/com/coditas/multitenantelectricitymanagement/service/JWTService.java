package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.SECRET}")
    private String secret;

    @Value("${jwt.EXPIRATION}")
    private long expiration;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) {
        return parseToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    public String extractRoles(String token) {
        return parseToken(token).get("role", String.class);
    }

    public SimpleGrantedAuthority getAuthorities(String token) {
        return new SimpleGrantedAuthority("ROLE_" + extractRoles(token));
    }

}

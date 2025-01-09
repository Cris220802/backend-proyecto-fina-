package com.proyecto.time.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyecto.time.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expirationTime}")
    private long jwtExpiration;

    @Value("${jwt.refreshTokenExpirationTime}")
    private long refreshExpiration;

    public String generateToken(final Usuario user) {
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(final Usuario user) {
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final Usuario user, final long expiration) {
        return Jwts.builder()
            .id(user.getId().toString())
            .claims(Map.of("name", user.getUsername()))
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        Claims jwtToken = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(String token, Usuario user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims jwtToken = Jwts.parser()
           .verifyWith(getSignInKey())
           .build()
           .parseSignedClaims(token)
           .getPayload();
           
        return jwtToken.getExpiration();
    }


}

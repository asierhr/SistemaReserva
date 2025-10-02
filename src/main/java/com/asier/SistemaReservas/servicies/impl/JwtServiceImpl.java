package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.servicies.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Override
    public String extractUser(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserEntity user) {
        final String username = extractUser(token);
        return(username.equals(user.getMail()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    @Override
    public String generateToken(UserEntity user) {
        return buildToken(user, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UserEntity user) {
        return buildToken(user, refreshExpiration);
    }

    @Override
    public String buildToken(UserEntity user, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", user.getUserRole().name());

        return Jwts.builder()
                .id(user.getMail())
                .claims(claims)
                .subject(user.getMail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    @Override
    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.UserEntity;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JwtService {
    String generateToken(UserEntity user);
    String generateRefreshToken(UserEntity user);
    String buildToken(UserEntity user, long expiration);
    SecretKey getKey();
    String extractUser(final String token);
    boolean isTokenValid(final String token, final UserEntity user);
    boolean isTokenExpired(final String token);
    Date extractExpiration(final String token);
}

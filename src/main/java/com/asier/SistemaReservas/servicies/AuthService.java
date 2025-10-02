package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.auth.LoginRequest;
import com.asier.SistemaReservas.auth.RegisterRequest;
import com.asier.SistemaReservas.auth.TokenResponse;
import com.asier.SistemaReservas.domain.entities.UserEntity;

public interface AuthService {
    TokenResponse register(RegisterRequest register);
    TokenResponse login(LoginRequest login);
    void saveUserToken(UserEntity user, String jwtToken);
    void revokeAllUserTokens(final UserEntity user);
}

package com.asier.SistemaReservas.system.auth.service;

import com.asier.SistemaReservas.system.auth.records.LoginRequest;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;

public interface AuthService {
    TokenResponse register(RegisterRequest register);
    TokenResponse login(LoginRequest login);
    void saveUserToken(UserEntity user, String jwtToken);
    void revokeAllUserTokens(final UserEntity user);
}

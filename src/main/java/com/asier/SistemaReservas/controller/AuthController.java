package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.auth.LoginRequest;
import com.asier.SistemaReservas.auth.RegisterRequest;
import com.asier.SistemaReservas.auth.TokenResponse;
import com.asier.SistemaReservas.servicies.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping(path = "/auth/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request){
        System.out.println("Controller received: " + request);
        final TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }


    @PostMapping(path = "/auth/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest request){
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }
}

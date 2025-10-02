package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        UserEntity user = userService.getUser();
        return ResponseEntity.ok(user);
    }
}

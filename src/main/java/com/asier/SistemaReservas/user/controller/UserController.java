package com.asier.SistemaReservas.user.controller;

import com.asier.SistemaReservas.user.domain.DTO.UserDTO;
import com.asier.SistemaReservas.user.service.UserService;
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
        UserDTO user = userService.getUser();
        return ResponseEntity.ok(user);
    }
}

package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String mail;
    private String password;
    private LocalDateTime createdAt;
    private UserRole userRole;
}

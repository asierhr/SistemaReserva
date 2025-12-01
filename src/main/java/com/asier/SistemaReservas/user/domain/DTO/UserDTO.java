package com.asier.SistemaReservas.user.domain.DTO;

import com.asier.SistemaReservas.user.domain.enums.UserRole;
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

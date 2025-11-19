package com.asier.SistemaReservas.auth;

import com.asier.SistemaReservas.domain.enums.UserRole;

public record RegisterRequest(
        String mail,
        String name,
        String password,
        UserRole role,
        Long hotelId,
        Long airportId
) {
}

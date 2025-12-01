package com.asier.SistemaReservas.system.auth.records;

import com.asier.SistemaReservas.user.domain.enums.UserRole;

public record RegisterRequest(
        String mail,
        String name,
        String password,
        UserRole role,
        Long hotelId,
        Long airportId
) {
}

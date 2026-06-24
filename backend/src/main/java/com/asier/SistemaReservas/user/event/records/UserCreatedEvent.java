package com.asier.SistemaReservas.user.event.records;

public record UserCreatedEvent(
        Long id,
        String name,
        String mail
) {
}

package com.asier.SistemaReservas.user.event.records;

import com.asier.SistemaReservas.user.domain.entity.UserEntity;

public record UserCreatedEvent(UserEntity user) {
}

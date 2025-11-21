package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}

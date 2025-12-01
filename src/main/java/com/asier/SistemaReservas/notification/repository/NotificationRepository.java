package com.asier.SistemaReservas.notification.repository;

import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}

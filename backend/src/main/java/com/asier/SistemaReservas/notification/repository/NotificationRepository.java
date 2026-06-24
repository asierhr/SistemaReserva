package com.asier.SistemaReservas.notification.repository;

import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("SELECT n FROM NotificationEntity n WHERE n.user.id = :userId AND n.status = :status")
    List<NotificationEntity> findNonReadNotifications(@Param("userId") Long id, @Param("status") NotificationStatus type);
}

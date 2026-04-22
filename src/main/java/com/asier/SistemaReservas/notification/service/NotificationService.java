package com.asier.SistemaReservas.notification.service;

import com.asier.SistemaReservas.notification.domain.DTO.NotificationDTO;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {
    void createNotification(NotificationEntity notification);
    List<NotificationDTO> getNonReadNotifications();
    void updateNotificationStatus(Long id);
}

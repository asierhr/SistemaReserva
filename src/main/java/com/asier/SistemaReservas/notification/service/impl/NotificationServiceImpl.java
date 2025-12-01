package com.asier.SistemaReservas.notification.service.impl;

import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.notification.repository.NotificationRepository;
import com.asier.SistemaReservas.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(NotificationEntity notification) {
        notificationRepository.save(notification);
    }
}

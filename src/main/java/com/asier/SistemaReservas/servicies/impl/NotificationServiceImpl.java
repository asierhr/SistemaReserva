package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.NotificationEntity;
import com.asier.SistemaReservas.repositories.NotificationRepository;
import com.asier.SistemaReservas.servicies.NotificationService;
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

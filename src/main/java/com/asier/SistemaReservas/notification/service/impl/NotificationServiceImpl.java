package com.asier.SistemaReservas.notification.service.impl;

import com.asier.SistemaReservas.notification.domain.DTO.NotificationDTO;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.notification.mapper.NotificationMapper;
import com.asier.SistemaReservas.notification.repository.NotificationRepository;
import com.asier.SistemaReservas.notification.service.NotificationService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(NotificationEntity notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getNonReadNotifications() {
        UserEntity user = userService.getUserEntity();
        List<NotificationEntity> notifications = notificationRepository.findNonReadNotifications(user.getId(), NotificationStatus.UNREAD);
        return notificationMapper.toDTOList(notifications);
    }

    @Override
    public void updateNotificationStatus(Long id) {
        NotificationEntity notification = notificationRepository.findById(id).orElseThrow();
        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }
}

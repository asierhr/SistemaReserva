package com.asier.SistemaReservas.notification.controller;

import com.asier.SistemaReservas.notification.domain.DTO.NotificationDTO;
import com.asier.SistemaReservas.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(path = "/notifications")
    public List<NotificationDTO> findNonReadNotifications(){
        return notificationService.getNonReadNotifications();
    }

    @PostMapping(path = "/notification/{id}")
    public void updateNotificationStatus(@PathVariable Long id){
        notificationService.updateNotificationStatus(id);
    }
}

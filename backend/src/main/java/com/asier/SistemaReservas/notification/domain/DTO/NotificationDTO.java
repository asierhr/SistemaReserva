package com.asier.SistemaReservas.notification.domain.DTO;

import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.notification.domain.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {
    private String topic;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
}

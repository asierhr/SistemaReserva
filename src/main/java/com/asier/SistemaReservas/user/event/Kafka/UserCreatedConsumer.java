package com.asier.SistemaReservas.user.event.Kafka;

import com.asier.SistemaReservas.email.service.EmailService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.notification.domain.enums.NotificationType;
import com.asier.SistemaReservas.notification.service.NotificationService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.domain.enums.UserRole;
import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedConsumer {
    private final ObjectMapper objectMapper;
    private final LoyaltyService loyaltyService;
    private final NotificationService notificationService;
    private final EmailService emailService;

    @KafkaListener(topics = "${topics.user-created}", groupId = "${kafka.group.loyalty}")
    public void listenLoyalty(String message, Acknowledgment ack) {
        try{
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            loyaltyService.createLoyaltyUser(event.user());
            ack.acknowledge();
        }catch (JsonProcessingException e) {
            log.error("Failed to parse message: {}", message, e);
            ack.acknowledge();
        }catch (Exception e) {
            log.error("Error creating loyalty for user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process loyalty", e);
        }
    }

    @KafkaListener(topics = "${topics.user-created}", groupId = "${kafka.group.notification}")
    public void listenNotification(String message, Acknowledgment ack) {
        try {
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            UserEntity user = event.user();
            NotificationEntity notification = NotificationEntity.builder()
                    .user(user)
                    .reservation(null)
                    .topic("Welcome " + user.getName())
                    .message("Welcome to out site " + user.getName())
                    .type(NotificationType.USER_CREATED)
                    .status(NotificationStatus.UNREAD)
                    .build();
            notificationService.createNotification(notification);
            ack.acknowledge();
        } catch (JsonProcessingException e) {
            log.error("Failed to parse message: {}", message, e);
            ack.acknowledge();
        }catch (Exception e) {
            log.error("Error creating notification: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process notification", e);
        }
    }

    @KafkaListener(topics = "${topics.user-created}", groupId = "${kafka.group.email}")
    public void listenEmail(String message, Acknowledgment ack){
        try{
            UserCreatedEvent event = objectMapper.readValue(message, UserCreatedEvent.class);
            emailService.sendWelcomeEmail(event.user());
            ack.acknowledge();
        }catch (JsonProcessingException e){
            log.error("Failed to parse message: {}", message, e);
            ack.acknowledge();
        }catch (Exception e) {
            log.error("Error creating email: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }
}

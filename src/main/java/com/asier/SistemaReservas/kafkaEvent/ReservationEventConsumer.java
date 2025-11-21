package com.asier.SistemaReservas.kafkaEvent;

import com.asier.SistemaReservas.domain.entities.NotificationEntity;
import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.domain.enums.NotificationType;
import com.asier.SistemaReservas.servicies.EmailService;
import com.asier.SistemaReservas.servicies.NotificationService;
import com.asier.SistemaReservas.servicies.ReservationService;
import com.asier.SistemaReservas.servicies.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationEventConsumer {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ReservationService reservationService;
    private final NotificationService notificationService;
    private final EmailService emailService;

    @KafkaListener(topics = "${topics.booking-created}", groupId = "notifications-group")
    public void listen(String message, Acknowledgment ack) throws JsonProcessingException {
        try {
            ReservationCreatedEvent event = objectMapper.readValue(message, ReservationCreatedEvent.class);
            UserEntity user = userService.getUserById(event.userId());
            ReservationEntity reservation = reservationService.getReservation(event.reservationId());
            NotificationEntity notification = NotificationEntity.builder()
                    .user(user)
                    .reservation(reservation)
                    .message("Your reservation with ID" + event.reservationId() + "has been created")
                    .type(NotificationType.RESERVATION_CONFIRMED)
                    .status(NotificationStatus.UNREAD)
                    .build();
            notificationService.createNotification(notification);

            emailService.sendReservationConfirmation(user, reservation);

            ack.acknowledge();
        }catch (JsonProcessingException e){
            log.error("Failed to parse message: {}", message, e);
        }
    }
}

package com.asier.SistemaReservas.reservation.event.Kafka;

import com.asier.SistemaReservas.email.service.EmailService;
import com.asier.SistemaReservas.notification.service.NotificationService;
import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.notification.domain.enums.NotificationType;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.payment.domain.records.CreatePaymentRequest;
import com.asier.SistemaReservas.payment.domain.records.PaymentResponse;
import com.asier.SistemaReservas.payment.service.PaymentService;
import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedKafkaEvent;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationEventConsumer {
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ReservationService reservationService;
    private final NotificationService notificationService;
    private final EmailService emailService;
    private final PaymentService paymentService;

    @KafkaListener(topics = "${topics.booking-created}", groupId = "notifications-group")
    public void listen(String message, Acknowledgment ack) throws JsonProcessingException {
        try {
            ReservationCreatedKafkaEvent event = objectMapper.readValue(message, ReservationCreatedKafkaEvent.class);
            processReservationEvent(event);
            ack.acknowledge();
        }catch (JsonProcessingException e){
            log.error("Failed to parse message: {}", message, e);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing reservation event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    private void processReservationEvent(ReservationCreatedKafkaEvent event){
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

        Map<String, String> metadata = Map.of(
                "reservationId", reservation.getId().toString(),
                "userId", user.getId().toString(),
                "checkInDate", reservation.getCheckedIn().toString()
        );

        CreatePaymentRequest paymentRequest = new CreatePaymentRequest(
                reservation.getId(),
                reservation.getTotalPrice(),
                "EUR",
                user.getMail(),
                metadata
        );

        PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);
        emailService.createEmailOutbox(user, reservation, notification, null, paymentResponse.clientSecret());
    }
}

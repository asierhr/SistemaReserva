package com.asier.SistemaReservas.kafkaEvent;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${topics.booking-created}")
    private String topic;

    public void sendReservationCreatedEvent(ReservationEntity reservation) throws JsonProcessingException {
        ReservationCreatedEvent event = new ReservationCreatedEvent(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getTotalPrice(),
                reservation.getReservationDate(),
                reservation.getBookingStatus()
        );
        String message = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(topic, message);
    }
}

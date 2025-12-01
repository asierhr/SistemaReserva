package com.asier.SistemaReservas.reservation.event.Kafka;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedKafkaEvent;
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

    public void sendReservationCreatedEvent(ReservationEntity reservation, String qrCodeBase64) throws JsonProcessingException {
        ReservationCreatedKafkaEvent event = new ReservationCreatedKafkaEvent(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getTotalPrice(),
                qrCodeBase64,
                reservation.getReservationDate(),
                reservation.getBookingStatus()
        );
        String message = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(topic, message);
    }
}

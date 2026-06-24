package com.asier.SistemaReservas.reservation.event.Kafka;

import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationEventListener {

    private final ReservationEventProducer reservationEventProducer;

    @EventListener
    @Async
    @Transactional
    public void onReservationCreated(ReservationCreatedEvent event) {
        try {
            reservationEventProducer.sendReservationCreatedEvent(event.reservation());
        } catch (JsonProcessingException e) {
            log.error("Failed to send reservation event for ID: {}",
                    event.reservation().getId(), e);
        }
    }
}

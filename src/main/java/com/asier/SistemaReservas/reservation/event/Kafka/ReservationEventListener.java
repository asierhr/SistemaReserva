package com.asier.SistemaReservas.reservation.event.Kafka;

import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedEvent;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.system.QR.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationEventListener {

    private final ReservationEventProducer reservationEventProducer;
    private final QRCodeService qrCodeService;

    @EventListener
    @Async
    public void onReservationCreated(ReservationCreatedEvent event){
        FlightReservationEntity flightReservation = event.flightReservation();
        try{
            String qrContent = qrCodeService.generateFlightQRContentJSON(
                    flightReservation.getId(),
                    flightReservation.getUser().getMail(),
                    flightReservation.getFlight().getAirline(),
                    flightReservation.getFlight().getDepartureTime().toString(),
                    flightReservation.getSeat().stream()
                            .map(s -> s.getSeatNumber())
                            .collect(Collectors.joining(", "))
            );

            log.info("QR Content: {}", qrContent);

            String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrContent, 300, 300);

            reservationEventProducer.sendReservationCreatedEvent(flightReservation, qrCodeBase64);

            log.info("Reservation sent to Kafka: {}", flightReservation.getId());

        } catch (Exception e) {
            log.error("Failed to send reservation event: {}", flightReservation.getId());
        }
    }
}

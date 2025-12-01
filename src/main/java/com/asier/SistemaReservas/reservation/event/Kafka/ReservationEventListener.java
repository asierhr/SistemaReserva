package com.asier.SistemaReservas.reservation.event.Kafka;

import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedEvent;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import com.asier.SistemaReservas.system.QR.QRCodeService;
import jakarta.transaction.Transactional;
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
    private final FlightReservationService flightReservationService;
    private final HotelReservationService hotelReservationService;

    @EventListener
    @Async
    @Transactional
    public void onReservationCreated(ReservationCreatedEvent event) {
        if (event.reservation() instanceof FlightReservationEntity) {
            FlightReservationEntity flightReservation = flightReservationService.getFlightById(event.reservation().getId());
            try {
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
                log.error("Failed to send reservation event for ID: {}",
                        flightReservation.getId(), e);
            }
        } else if (event.reservation() instanceof HotelReservationEntity) {
            HotelReservationEntity hotelReservation = hotelReservationService.getReservationById(event.reservation().getId());
            try {
                String qrContent = qrCodeService.generateHotelQRContentJSON(
                        hotelReservation.getId(),
                        hotelReservation.getUser().getMail(),
                        hotelReservation.getHotel().getHotelName(),
                        hotelReservation.getCheckIn().toString(),
                        hotelReservation.getCheckOut().toString()
                );

                log.info("QR Content: {}", qrContent);

                System.out.println("Hola");

                String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrContent, 300, 300);

                System.out.println("Hola");

                reservationEventProducer.sendReservationCreatedEvent(hotelReservation, qrCodeBase64);

                log.info("Reservation sent to Kafka: {}", hotelReservation.getId());

            } catch (Exception e) {
                log.error("Failed to send reservation event for ID: {}",
                        hotelReservation.getId(), e);
            }
        }
    }
}

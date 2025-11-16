package com.asier.SistemaReservas.config;

import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.servicies.FlightReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredReservationCleanupJob {
    private final FlightReservationService flightReservationService;

    @Scheduled(fixedRate = 300_000)
    @Transactional
    public void releaseExpiredReservations(){
        List<FlightReservationEntity> expiredReservations = flightReservationService.getFlightReservationsExpired();

        if (expiredReservations.isEmpty()) {
            log.info("No expired reservations found.");
            return;
        }


        for(FlightReservationEntity reservation: expiredReservations){
            reservation.setBookingStatus(BookingStatus.EXPIRED);
            List<SeatEntity> seats = reservation.getSeat();
            for(SeatEntity seat: seats){
                seat.setReservation(null);
                seat.setAvailable(true);
            }
        }
        flightReservationService.updateFlightsReservations(expiredReservations);
    }
}

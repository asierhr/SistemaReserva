package com.asier.SistemaReservas.reservation.Job;

import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredReservationCleanupJob extends QuartzJobBean {
    private final FlightReservationService flightReservationService;

    @Override
    @Transactional
    public void executeInternal(JobExecutionContext context){
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
        log.info("Expired reservations updated in memory, transaction should persist changes on commit.");
    }
}

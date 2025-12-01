package com.asier.SistemaReservas.reservation.flightReservation.Job;

import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoShowFlightJob extends QuartzJobBean {
    private final FlightReservationService flightReservationService;
    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            List<FlightReservationEntity> flightReservations = flightReservationService.getAllCheckInFalseAndFlightDone();

            flightReservations.forEach(reservation -> {
                reservation.setBookingStatus(BookingStatus.NOT_SHOWN);
            });

            flightReservationService.updateFlightsReservations(flightReservations);
        }catch (Exception e){
            throw new JobExecutionException("Error en verificación de check-in", e);
        }
    }
}

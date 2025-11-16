package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.ReservationDTO;
import com.asier.SistemaReservas.domain.entities.*;
import com.asier.SistemaReservas.repositories.ReservationRepository;
import com.asier.SistemaReservas.servicies.ReservationService;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;

    @Override
    public ReservationEntity getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public void updateReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void refundReservation(ReservationEntity reservation) {
        if(reservation instanceof FlightReservationEntity flightReservation){
            List<SeatEntity> seats = flightReservation.getSeat();
            for(SeatEntity seat: seats) {
                seat.setAvailable(true);
            }
        }else if(reservation instanceof HotelReservationEntity hotelReservation){
            hotelReservation.setCheckIn(null);
            hotelReservation.setCheckOut(null);
        }
        updateReservation(reservation);
    }
}

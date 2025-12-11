package com.asier.SistemaReservas.reservation.service.impl;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.repository.ReservationRepository;
import com.asier.SistemaReservas.reservation.service.ReservationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReservationHelperImpl implements ReservationHelper {
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationEntity getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public void updateReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }
}

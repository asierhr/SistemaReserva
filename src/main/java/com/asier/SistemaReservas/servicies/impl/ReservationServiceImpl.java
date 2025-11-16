package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.asier.SistemaReservas.repositories.ReservationRepository;
import com.asier.SistemaReservas.servicies.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
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

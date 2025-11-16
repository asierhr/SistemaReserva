package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;

public interface ReservationService {
    ReservationEntity getReservation(Long Id);
    void updateReservation(ReservationEntity reservation);
}

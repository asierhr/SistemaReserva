package com.asier.SistemaReservas.reservation.service;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;

public interface ReservationHelper {
    ReservationEntity getReservation(Long Id);
    void updateReservation(ReservationEntity reservation);
}

package com.asier.SistemaReservas.reservation.service;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;

public interface ReservationService {
    ReservationEntity getReservation(Long Id);
    void updateReservation(ReservationEntity reservation);
    void refundReservation(ReservationEntity reservation);
}

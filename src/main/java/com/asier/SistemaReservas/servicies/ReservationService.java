package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.ReservationDTO;
import com.asier.SistemaReservas.domain.entities.ReservationEntity;

import java.util.List;

public interface ReservationService {
    ReservationEntity getReservation(Long Id);
    void updateReservation(ReservationEntity reservation);
    void refundReservation(ReservationEntity reservation);
}

package com.asier.SistemaReservas.reservation.service;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.domain.records.CheckInResponse;
import com.asier.SistemaReservas.system.QR.domain.records.QRValidationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ReservationService {
    ReservationEntity getReservation(Long Id);
    void updateReservation(ReservationEntity reservation);
    void refundReservation(ReservationEntity reservation);
    CheckInResponse validateQR(QRValidationRequest request) throws JsonProcessingException;
}

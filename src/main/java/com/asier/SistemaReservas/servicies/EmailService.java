package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;

public interface EmailService {
    void sendReservationConfirmation(UserEntity user, ReservationEntity reservation);
}

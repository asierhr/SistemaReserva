package com.asier.SistemaReservas.email.service;

import com.asier.SistemaReservas.email.domain.entity.EmailOutboxEntity;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;

import java.util.List;

public interface EmailService {
    void sendReservationConfirmation(UserEntity user, ReservationEntity reservation, String qrCodeBase64);
    void createEmailOutbox(UserEntity user, ReservationEntity reservation, NotificationEntity notification, String qrCodeBase64);
    void updateEmailOutbox(EmailOutboxEntity emailOutbox);
    List<EmailOutboxEntity> findEmailsPendingToProcess(int limit);
}

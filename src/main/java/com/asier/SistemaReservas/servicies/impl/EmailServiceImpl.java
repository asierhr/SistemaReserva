package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.servicies.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendReservationConfirmation(UserEntity user, ReservationEntity reservation) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(user.getMail());
            message.setSubject("Reservation Confirmed - #" + reservation.getId());
            message.setText(buildConfirmationMessage(user, reservation));

            mailSender.send(message);
            log.info("Confirmation email sent to {}", user.getMail());
        } catch (Exception e) {
            log.error("Failed to send email to {}", user.getMail(), e);
        }
    }

    private String buildConfirmationMessage(UserEntity user, ReservationEntity reservation){
        String checkoutLink = "http://localhost:8080/payments/" + reservation.getId();
        return String.format("""
                Hello %s
                
                Your reservation has been confirmed
                
                Reservation ID: %s
                Date: %s
                Total = $%.2f
                
                Complete your payment here:
                %s
                
                Thank you for your booking
                """,
                user.getName(),
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getTotalPrice(),
                checkoutLink
        );
    }
}

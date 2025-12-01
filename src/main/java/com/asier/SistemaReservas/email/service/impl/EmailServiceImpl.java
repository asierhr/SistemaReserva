package com.asier.SistemaReservas.email.service.impl;

import com.asier.SistemaReservas.email.domain.entity.EmailOutboxEntity;
import com.asier.SistemaReservas.email.domain.enums.OutboxStatus;
import com.asier.SistemaReservas.email.repository.EmailOutboxRepository;
import com.asier.SistemaReservas.email.service.EmailService;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final EmailOutboxRepository emailOutboxRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendReservationConfirmation(UserEntity user, ReservationEntity reservation, String qrCodeBase64) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getMail());
            helper.setSubject("Reservation Confirmed - #" + reservation.getId());
            helper.setText(buildConfirmationMessageHTML(user, reservation, qrCodeBase64), true);

            if (qrCodeBase64 != null && !qrCodeBase64.isEmpty()) {
                byte[] qrBytes = Base64.getDecoder().decode(qrCodeBase64);
                helper.addInline("qrCode", new ByteArrayResource(qrBytes), "image/png");
                log.info("✅ QR code attached inline");
            }

            mailSender.send(message);
            log.info("✅ Email sent successfully to {}", user.getMail());
        } catch (Exception e) {
            log.error("Failed to send email to {}", user.getMail(), e);
        }
    }

    private String buildConfirmationMessageHTML(UserEntity user, ReservationEntity reservation, String qrCodeBase64) {
        String checkoutLink = "http://localhost:8080/payments/" + reservation.getId();

        String reservationType;
        String details;

        if (reservation instanceof FlightReservationEntity flightRes) {
            reservationType = "Flight";
            details = String.format("""
                <p><strong>Flight:</strong> %s</p>
                <p><strong>Departure:</strong> %s</p>
                <p><strong>Seats:</strong> %s</p>
                """,
                    flightRes.getFlight().getAirline(),
                    flightRes.getFlight().getDepartureTime(),
                    flightRes.getSeat().stream()
                            .map(SeatEntity::getSeatNumber)
                            .collect(Collectors.joining(", "))
            );
        } else if (reservation instanceof HotelReservationEntity hotelRes) {
            reservationType = "Hotel";
            details = String.format("""
                <p><strong>Hotel:</strong> %s</p>
                <p><strong>Check-in:</strong> %s</p>
                <p><strong>Check-out:</strong> %s</p>
                """,
                    hotelRes.getHotel().getHotelName(),
                    hotelRes.getCheckIn(),
                    hotelRes.getCheckOut()
            );
        } else {
            reservationType = "Reservation";
            details = "";
        }

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .header {
                        background-color: #0066cc;
                        color: white;
                        padding: 20px;
                        text-align: center;
                        border-radius: 5px 5px 0 0;
                    }
                    .content {
                        background-color: #f9f9f9;
                        padding: 30px;
                        border: 1px solid #ddd;
                    }
                    .qr-section {
                        text-align: center;
                        margin: 30px 0;
                        padding: 20px;
                        background-color: white;
                        border-radius: 5px;
                    }
                    .qr-code {
                        width: 250px;
                        height: 250px;
                        margin: 20px auto;
                        display: block;
                    }
                    .button {
                        display: inline-block;
                        padding: 12px 30px;
                        background-color: #0066cc;
                        color: white;
                        text-decoration: none;
                        border-radius: 5px;
                        margin: 20px 0;
                    }
                    .footer {
                        text-align: center;
                        margin-top: 30px;
                        padding-top: 20px;
                        border-top: 1px solid #ddd;
                        color: #666;
                        font-size: 12px;
                    }
                    .info-box {
                        background-color: white;
                        padding: 15px;
                        border-left: 4px solid #0066cc;
                        margin: 20px 0;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>✈️ %s Reservation Confirmed</h1>
                </div>
                
                <div class="content">
                    <h2>Hello %s,</h2>
                    <p>Your reservation has been successfully confirmed! 🎉</p>
                    
                    <div class="info-box">
                        <p><strong>Reservation ID:</strong> #%s</p>
                        <p><strong>Date:</strong> %s</p>
                        %s
                        <p><strong>Total Amount:</strong> <span style="font-size: 20px; color: #0066cc;">$%.2f</span></p>
                    </div>
                    
                    <div class="qr-section">
                        <h3>📱 Your Boarding Pass</h3>
                        <p>Present this QR code at check-in:</p>
                        <img src="data:image/png;base64,%s" alt="QR Code" class="qr-code"/>
                        <p style="color: #666; font-size: 14px;">
                            💡 Save this email or take a screenshot for easy access
                        </p>
                    </div>
                    
                    <div style="text-align: center;">
                        <p><strong>⚠️ Complete your payment to confirm:</strong></p>
                        <a href="%s" class="button">Complete Payment Now</a>
                        <p style="color: #666; font-size: 14px;">
                            ⏰ Payment must be completed within 15 minutes
                        </p>
                    </div>
                </div>
                
                <div class="footer">
                    <p>Questions? Contact us at support@yourcompany.com</p>
                    <p>© 2025 Flight Reservation System. All rights reserved.</p>
                </div>
            </body>
            </html>
            """,
                reservationType,
                user.getName(),
                reservation.getId(),
                reservation.getReservationDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")),
                details,
                reservation.getTotalPrice(),
                qrCodeBase64,
                checkoutLink
        );
    }

    @Override
    public void createEmailOutbox(UserEntity user, ReservationEntity reservation, NotificationEntity notification, String qrCodeBase64) {
        EmailOutboxEntity emailOutbox = EmailOutboxEntity.builder()
                .user(user)
                .reservation(reservation)
                .notification(notification)
                .qrCodeBase64(qrCodeBase64)
                .attempts(0)
                .createdAt(LocalDateTime.now())
                .outboxStatus(OutboxStatus.PENDING)
                .build();
        emailOutboxRepository.save(emailOutbox);
    }

    @Override
    public void updateEmailOutbox(EmailOutboxEntity emailOutbox) {
        emailOutboxRepository.save(emailOutbox);
    }

    @Override
    public List<EmailOutboxEntity> findEmailsPendingToProcess(int limit) {
        Pageable pageable = PageRequest.of(0,limit);
        return emailOutboxRepository.findPendingToProcess(OutboxStatus.PENDING, LocalDateTime.now(), pageable);
    }
}

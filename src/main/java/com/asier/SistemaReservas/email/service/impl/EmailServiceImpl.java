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
import java.time.temporal.ChronoUnit;
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

    @Override
    @Async
    public void sendPendingPaymentReservation(UserEntity user, ReservationEntity reservation, String clientSecret) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getMail());
            helper.setSubject("Reservation Confirmed - #" + reservation.getId());
            helper.setText(buildPendingPaymentMessageHTML(user,reservation,clientSecret), true);

            mailSender.send(message);
            log.info("✅ Email sent successfully to {}", user.getMail());
        } catch (Exception e) {
            log.error("Failed to send email to {}", user.getMail(), e);
        }
    }

    @Override
    @Async
    public void sendRefundedPaymentConfirmation(UserEntity user, ReservationEntity reservation) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(user.getMail());
            helper.setSubject("Reservation Confirmed - #" + reservation.getId());
            helper.setText(buildRefundedPaymentMessageHTML(user,reservation), true);

            mailSender.send(message);
            log.info("✅ Email sent successfully to {}", user.getMail());
        } catch (Exception e) {
            log.error("Failed to send email to {}", user.getMail(), e);
        }
    }

    private String buildRefundedPaymentMessageHTML(UserEntity user, ReservationEntity reservation) {

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
                    background-color: #4CAF50;
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
                .success-icon {
                    text-align: center;
                    font-size: 60px;
                    margin: 20px 0;
                }
                .info-box {
                    background-color: white;
                    padding: 15px;
                    border-left: 4px solid #4CAF50;
                    margin: 20px 0;
                }
                .refund-amount {
                    text-align: center;
                    background-color: #e8f5e9;
                    padding: 20px;
                    border-radius: 5px;
                    margin: 20px 0;
                }
                .refund-amount .amount {
                    font-size: 32px;
                    color: #4CAF50;
                    font-weight: bold;
                }
                .timeline {
                    background-color: white;
                    padding: 20px;
                    border-radius: 5px;
                    margin: 20px 0;
                }
                .timeline-item {
                    padding: 10px 0;
                    border-left: 3px solid #4CAF50;
                    padding-left: 20px;
                    margin-left: 10px;
                }
                .footer {
                    text-align: center;
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 1px solid #ddd;
                    color: #666;
                    font-size: 12px;
                }
            </style>
        </head>
        <body>
            <div class="header">
                <h1>💰 Refund Processed Successfully</h1>
            </div>
            
            <div class="content">
                <div class="success-icon">✅</div>
                
                <h2>Hello %s,</h2>
                <p>Good news! Your refund has been processed successfully.</p>
                
                <div class="refund-amount">
                    <p style="margin: 0; font-size: 16px; color: #666;">Refunded Amount</p>
                    <div class="amount">$%.2f</div>
                    <p style="margin: 5px 0 0 0; color: #666; font-size: 14px;">
                        The funds will be returned to your original payment method
                    </p>
                </div>
                
                <div class="info-box">
                    <h3>📋 Cancelled Reservation Details</h3>
                    <p><strong>Reservation Type:</strong> %s</p>
                    <p><strong>Reservation ID:</strong> #%s</p>
                    <p><strong>Original Date:</strong> %s</p>
                    %s
                </div>
                
                <div class="timeline">
                    <h3>⏱️ What Happens Next?</h3>
                    <div class="timeline-item">
                        <strong>Step 1:</strong> Refund initiated (Completed ✓)
                    </div>
                    <div class="timeline-item">
                        <strong>Step 2:</strong> Processing by payment provider (3-5 business days)
                    </div>
                    <div class="timeline-item">
                        <strong>Step 3:</strong> Funds appear in your account (5-10 business days)
                    </div>
                </div>
                
                <div style="background-color: #fff3cd; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p style="margin: 0;">
                        <strong>📌 Important:</strong> The refund timeline may vary depending on your bank or card issuer. 
                        If you don't see the funds after 10 business days, please contact your bank or reach out to us.
                    </p>
                </div>
                
                <p>We're sorry to see you cancel your reservation. If there's anything we could have done better, 
                   please don't hesitate to let us know.</p>
                
                <p style="margin-top: 30px;">Thank you for choosing our service. We hope to serve you again in the future!</p>
            </div>
            
            <div class="footer">
                <p>Questions about your refund? Contact us at support@yourcompany.com</p>
                <p>© 2025 Reservation System. All rights reserved.</p>
            </div>
        </body>
        </html>
        """,
                user.getName(),
                reservation.getTotalPrice(),
                reservationType,
                reservation.getId(),
                reservation.getReservationDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")),
                details
        );
    }

    private String buildPendingPaymentMessageHTML(UserEntity user, ReservationEntity reservation, String clientSecret){
        String paymentUrl = "/checkout?client_secret=" + clientSecret; //falta el frontend url

        String reservationType = "";
        String specificDetails = "";

        if (reservation instanceof FlightReservationEntity) {
            FlightReservationEntity flight = (FlightReservationEntity) reservation;
            reservationType = "Flight";
            specificDetails = String.format("""
            <p><strong>Airline:</strong> %s</p>
            <p><strong>Flight Number:</strong> %s</p>
            <p><strong>Departure:</strong> %s</p>
            <p><strong>Arrival:</strong> %s</p>
            """,
                    flight.getFlight().getAirline(),
                    flight.getFlight().getId(),
                    flight.getFlight().getDepartureTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")),
                    flight.getFlight().getArrivalTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"))
            );
        } else if (reservation instanceof HotelReservationEntity) {
            HotelReservationEntity hotel = (HotelReservationEntity) reservation;
            reservationType = "Hotel";
            long nights = ChronoUnit.DAYS.between(hotel.getCheckIn(), hotel.getCheckOut());
            specificDetails = String.format("""
            <p><strong>Hotel:</strong> %s</p>
            <p><strong>Check-in:</strong> %s</p>
            <p><strong>Check-out:</strong> %s</p>
            <p><strong>Nights:</strong> %d</p>
            """,
                    hotel.getHotel().getHotelName(),
                    hotel.getCheckIn().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    hotel.getCheckOut().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    nights
            );
        }

        return String.format("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Complete Your Payment</title>
        </head>
        <body style="margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; background-color: #f5f5f5;">
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f5f5f5; padding: 40px 0;">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                            
                            <!-- Header -->
                            <tr>
                                <td style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 40px; text-align: center; border-radius: 8px 8px 0 0;">
                                    <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">
                                        Complete Your Payment 💳
                                    </h1>
                                </td>
                            </tr>
                            
                            <!-- Greeting -->
                            <tr>
                                <td style="padding: 30px 40px 20px;">
                                    <h2 style="margin: 0 0 10px; color: #2d3748; font-size: 24px;">
                                        Hi %s! 👋
                                    </h2>
                                    <p style="margin: 0; color: #4a5568; font-size: 16px; line-height: 1.6;">
                                        Your %s reservation has been created successfully and is waiting for payment confirmation.
                                    </p>
                                </td>
                            </tr>
                            
                            <!-- Reservation Details -->
                            <tr>
                                <td style="padding: 0 40px 20px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f7fafc; border-radius: 8px; padding: 25px;">
                                        <tr>
                                            <td>
                                                <h3 style="margin: 0 0 15px; color: #2d3748; font-size: 18px; font-weight: 600;">
                                                    📋 Reservation Details
                                                </h3>
                                                <table width="100%%" cellpadding="5" cellspacing="0">
                                                    <tr>
                                                        <td style="color: #718096; font-size: 14px;">Reservation ID:</td>
                                                        <td align="right" style="color: #2d3748; font-size: 14px; font-weight: 600;">#%s</td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2" style="padding-top: 10px;">
                                                            %s
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 15px; border-top: 2px solid #e2e8f0; color: #2d3748; font-size: 16px; font-weight: 600;">
                                                            Total Amount:
                                                        </td>
                                                        <td align="right" style="padding-top: 15px; border-top: 2px solid #e2e8f0; color: #667eea; font-size: 20px; font-weight: 700;">
                                                            €%.2f
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- Important Notice -->
                            <tr>
                                <td style="padding: 0 40px 20px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fff3cd; border-left: 4px solid #ffc107; border-radius: 4px; padding: 15px;">
                                        <tr>
                                            <td>
                                                <p style="margin: 0; color: #856404; font-size: 14px; line-height: 1.6;">
                                                    <strong>⚠️ IMPORTANT:</strong> Your reservation is not confirmed yet. 
                                                    Please complete the payment within the next 24 hours to secure your booking.
                                                </p>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- CTA Button -->
                            <tr>
                                <td style="padding: 0 40px 30px;" align="center">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="center" style="border-radius: 6px; background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 2px;">
                                                <a href="%s" 
                                                   style="display: inline-block; padding: 16px 48px; color: #ffffff; text-decoration: none; font-size: 16px; font-weight: 600; border-radius: 5px; background-color: transparent;">
                                                    Complete Payment Now →
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
                                    <p style="margin: 15px 0 0; color: #718096; font-size: 12px;">
                                        Or copy this link: <a href="%s" style="color: #667eea; word-break: break-all;">%s</a>
                                    </p>
                                </td>
                            </tr>
                            
                            <!-- Next Steps -->
                            <tr>
                                <td style="padding: 0 40px 30px;">
                                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #ebf8ff; border-radius: 8px; padding: 20px;">
                                        <tr>
                                            <td>
                                                <h4 style="margin: 0 0 10px; color: #2c5282; font-size: 16px;">
                                                    📱 What happens next?
                                                </h4>
                                                <ol style="margin: 10px 0 0 20px; padding: 0; color: #2d3748; font-size: 14px; line-height: 1.8;">
                                                    <li>Click the button above to complete your payment</li>
                                                    <li>Your payment will be securely processed by Stripe</li>
                                                    <li>You'll receive a confirmation email with your QR code</li>
                                                    <li>Show the QR code at check-in</li>
                                                </ol>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            
                            <!-- Footer -->
                            <tr>
                                <td style="padding: 30px 40px; background-color: #f7fafc; border-radius: 0 0 8px 8px; text-align: center;">
                                    <p style="margin: 0 0 10px; color: #4a5568; font-size: 14px;">
                                        Need help? Contact our support team
                                    </p>
                                    <p style="margin: 0; color: #a0aec0; font-size: 12px;">
                                        If you didn't make this reservation, please ignore this email.
                                    </p>
                                    <p style="margin: 15px 0 0; color: #a0aec0; font-size: 12px;">
                                        © 2024 Your Company. All rights reserved.
                                    </p>
                                </td>
                            </tr>
                            
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """,
                user.getName(),
                reservationType,
                reservation.getId(),
                specificDetails,
                reservation.getTotalPrice(),
                paymentUrl,
                paymentUrl,
                paymentUrl
        );
    }

    private String buildConfirmationMessageHTML(UserEntity user, ReservationEntity reservation, String qrCodeBase64) {

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
                qrCodeBase64
        );
    }

    @Override
    public void createEmailOutbox(UserEntity user, ReservationEntity reservation, NotificationEntity notification, String qrCodeBase64, String clientSecret) {
        EmailOutboxEntity emailOutbox = EmailOutboxEntity.builder()
                .user(user)
                .reservation(reservation)
                .notification(notification)
                .qrCodeBase64(qrCodeBase64)
                .clientSecret(clientSecret)
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

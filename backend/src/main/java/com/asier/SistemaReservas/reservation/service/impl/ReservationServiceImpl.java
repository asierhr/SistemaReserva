package com.asier.SistemaReservas.reservation.service.impl;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.service.LoyaltyBenefitsService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.payment.service.PaymentService;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.domain.records.CheckInResponse;
import com.asier.SistemaReservas.reservation.exceptions.ReservationNotFoundException;
import com.asier.SistemaReservas.reservation.repository.ReservationRepository;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import com.asier.SistemaReservas.system.QR.domain.records.QRValidationRequest;
import com.asier.SistemaReservas.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final LoyaltyBenefitsService loyaltyBenefitsService;
    private final LoyaltyService loyaltyService;
    private final PaymentService paymentService;

    @Override
    public ReservationEntity getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public void updateReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void refundReservation(ReservationEntity reservation) {
        LoyaltyTierEntity tier = loyaltyService.getLoyaltyByUser(userService.getUserEntity().getId()).getLoyaltyTier();
        BigDecimal cancellationFee = loyaltyBenefitsService.getCancellationFee(
                tier,
                reservation.getTotalPriceAfterDiscount(),
                LocalDateTime.now(),
                reservation.getCancellationDeadline(),
                reservation.getReservationDate()
        );
        BigDecimal refundedAmount = reservation.getTotalPriceAfterDiscount().subtract(cancellationFee);

        reservation.setRefundedAmount(refundedAmount);
        reservation.setBookingStatus(BookingStatus.CANCELLED);
        reservation.setCancelledAt(LocalDateTime.now());

        reservationRepository.save(reservation);

        paymentService.initiateRefund(reservation,refundedAmount);
    }

    @Override
    public CheckInResponse validateQR(QRValidationRequest request) throws JsonProcessingException {
        JsonNode qrData = objectMapper.readTree(request.qrContent());
        String type = qrData.get("type").asText();
        Long reservationId = qrData.get("reservationId").asLong();
        String email = qrData.get("email").asText();


        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));


        if (!reservation.getUser().getMail().equals(email)) {
            return new CheckInResponse(false, "QR does not match reservation owner");
        }


        if (reservation.getBookingStatus() != BookingStatus.PAID) {
            return new CheckInResponse(false,
                    "Reservation not confirmed. Status: " + reservation.getBookingStatus());
        }
        if (Boolean.TRUE.equals(reservation.getCheckedIn())) {
            return new CheckInResponse(true,
                    "Already checked in at " + reservation.getCheckInTime());
        }
        reservation.setCheckedIn(true);
        reservation.setCheckInTime(LocalDateTime.now());
        reservationRepository.save(reservation);

        log.info("✅ Check-in successful for reservation: {}", reservationId);

        return new CheckInResponse(true, "Check-in successful!");
    }
}

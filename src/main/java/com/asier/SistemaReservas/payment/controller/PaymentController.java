package com.asier.SistemaReservas.payment.controller;

import com.asier.SistemaReservas.payment.domain.records.CreatePaymentRequest;
import com.asier.SistemaReservas.payment.domain.records.PaymentResponse;
import com.asier.SistemaReservas.payment.service.PaymentService;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;
    private final ReservationService reservationService;

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable Long reservationId, @RequestBody CreatePaymentRequest request) {
        ReservationEntity reservation = reservationService.getReservation(reservationId);
        log.info("Received create payment request: {}", request);
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {

        paymentService.processWebhook(payload, signature);
        return ResponseEntity.ok("Webhook processed");
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable Long paymentId) {
        PaymentResponse response = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<String> initiateRefund(
            @RequestParam Long reservationId,
            @RequestParam BigDecimal amount
    ) {
        paymentService.initiateRefund(reservationId, amount);
        return ResponseEntity.ok("Refund initiated successfully");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        log.info("Received Stripe webhook");
        paymentService.processWebhook(payload, sigHeader);
        return ResponseEntity.ok("Webhook processed");
    }
}

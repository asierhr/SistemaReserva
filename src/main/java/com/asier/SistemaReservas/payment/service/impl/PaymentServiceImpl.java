package com.asier.SistemaReservas.payment.service.impl;

import com.asier.SistemaReservas.email.service.EmailService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import com.asier.SistemaReservas.notification.domain.enums.NotificationStatus;
import com.asier.SistemaReservas.notification.domain.enums.NotificationType;
import com.asier.SistemaReservas.notification.service.NotificationService;
import com.asier.SistemaReservas.payment.exception.PaymentCardException;
import com.asier.SistemaReservas.payment.exception.PaymentConfigurationException;
import com.asier.SistemaReservas.payment.repository.PaymentRepository;
import com.asier.SistemaReservas.payment.domain.records.PaymentResponse;
import com.asier.SistemaReservas.payment.domain.entity.PaymentEntity;
import com.asier.SistemaReservas.payment.domain.enums.PaymentMethod;
import com.asier.SistemaReservas.payment.domain.enums.PaymentStatus;
import com.asier.SistemaReservas.payment.domain.records.CreatePaymentRequest;
import com.asier.SistemaReservas.payment.service.PaymentService;
import com.asier.SistemaReservas.system.QR.QRCodeService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Value;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import com.asier.SistemaReservas.payment.exception.PaymentNotFoundException;
import com.asier.SistemaReservas.payment.exception.PaymentProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationService reservationService;
    private final LoyaltyService loyaltyService;
    private final EmailService emailService;
    private final QRCodeService qrCodeService;
    private final NotificationService notificationService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Value("${stripe.currency:EUR}")
    private String defaultCurrency;

    @Transactional
    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        log.info("Creating payment for reservation: {}", request.reservationId());

        ReservationEntity reservation = reservationService.getReservation(request.reservationId());

        if (reservation.getBookingStatus() == BookingStatus.PAID) {
            throw new IllegalStateException("Reservation already paid");
        }

        Optional<PaymentEntity> existingPayment = paymentRepository
                .findByReservationIdAndStatus(request.reservationId(), PaymentStatus.PENDING);

        if (existingPayment.isPresent()) {
            log.info("Reusing existing payment intent: {}", existingPayment.get().getStripePaymentIntentId());
            return mapToResponse(existingPayment.get());
        }

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(toStripeAmount(request.amount()))
                    .setCurrency(request.currency() != null ? request.currency().toLowerCase() : defaultCurrency.toLowerCase())
                    .setDescription("Reservation #" + request.reservationId())
                    .putMetadata("reservation_id", request.reservationId().toString())
                    .putMetadata("customer_email", request.customerEmail() != null ? request.customerEmail() : "")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            PaymentEntity payment = PaymentEntity.builder()
                    .stripePaymentIntentId(paymentIntent.getId())
                    .reservation(reservation)
                    .amount(request.amount())
                    .currency(request.currency() != null ? request.currency() : defaultCurrency)
                    .status(PaymentStatus.PENDING)
                    .paymentMethod(PaymentMethod.CARD)
                    .build();

            paymentRepository.save(payment);

            log.info("✅ Payment intent created: {}", paymentIntent.getId());

            return PaymentResponse.builder()
                    .paymentId(payment.getId())
                    .stripePaymentIntentId(paymentIntent.getId())
                    .clientSecret(paymentIntent.getClientSecret())
                    .status(PaymentStatus.PENDING)
                    .amount(request.amount())
                    .currency(request.currency() != null ? request.currency() : defaultCurrency)
                    .createdAt(payment.getCreatedAt())
                    .build();

        } catch (CardException e) {
            log.error("Card error: {}", e.getMessage());
            throw new PaymentCardException("Card declined: " + e.getMessage(), e);

        } catch (RateLimitException e) {
            log.error("Rate limit exceeded", e);
            throw new PaymentProcessingException("Too many requests, try again later", e);

        } catch (InvalidRequestException e) {
            log.error("Invalid request to Stripe: {}", e.getMessage());
            throw new PaymentProcessingException("Invalid payment request: " + e.getMessage(), e);

        } catch (AuthenticationException e) {
            log.error("Authentication error with Stripe", e);
            throw new PaymentConfigurationException("Payment system authentication failed", e);

        } catch (ApiConnectionException e) {

            log.error("Network error connecting to Stripe", e);
            throw new PaymentProcessingException("Payment service unavailable, try again", e);

        } catch (ApiException e) {

            log.error("Stripe API error", e);
            throw new PaymentProcessingException("Payment processing failed: " + e.getMessage(), e);

        } catch (StripeException e) {

            log.error("Unexpected Stripe error", e);
            throw new PaymentProcessingException("Payment failed: " + e.getMessage(), e);
        }
    }


    @Transactional
    @Override
    public void processWebhook(String payload, String signatureHeader) {
        log.info("Processing Stripe webhook");

        Event event;

        try {
            event = Webhook.constructEvent(payload, signatureHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            log.error("Invalid webhook signature!");
            throw new SecurityException("Invalid webhook signature", e);
        } catch (Exception e) {
            log.error("Error parsing webhook", e);
            throw new PaymentProcessingException("Failed to process webhook", e);
        }

        log.info("Webhook event type: {}", event.getType());

        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;

            case "payment_intent.payment_failed":
                handlePaymentIntentFailed(event);
                break;

            case "payment_intent.requires_action":
                handlePaymentIntentRequiresAction(event);
                break;

            case "charge.refunded":
                handleChargeRefunded(event);
                break;

            default:
                log.info("Unhandled webhook event type: {}", event.getType());
        }
    }

    private void handlePaymentIntentSucceeded(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new IllegalStateException("PaymentIntent not found in event"));

        log.info("Payment succeeded: {}", paymentIntent.getId());

        PaymentEntity payment = paymentRepository
                .findByStripePaymentIntentId(paymentIntent.getId())
                .orElseThrow(() -> new PaymentNotFoundException(paymentIntent.getId()));

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            log.info("Payment already processed, skipping: {}", paymentIntent.getId());
            return;
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        payment.setWebhookReceivedAt(LocalDateTime.now());

        if (paymentIntent.getLatestChargeObject() != null) {
            Charge charge = paymentIntent.getLatestChargeObject();
            payment.setStripeChargeId(charge.getId());

            if (charge.getPaymentMethodDetails() != null &&
                    charge.getPaymentMethodDetails().getCard() != null) {
                payment.setCardLast4(charge.getPaymentMethodDetails().getCard().getLast4());
                payment.setCardBrand(charge.getPaymentMethodDetails().getCard().getBrand());
            }
        }

        paymentRepository.save(payment);

        ReservationEntity reservation = payment.getReservation();
        reservation.setBookingStatus(BookingStatus.PAID);
        reservationService.updateReservation(reservation);

        processInformation(reservation, payment);

        log.info("✅ Payment and reservation updated successfully");
    }

    @Transactional
    private void processInformation(ReservationEntity reservation, PaymentEntity payment){
        loyaltyService.updateLoyalty(reservation.getUser().getId(), payment.getAmount().intValue());

        String qrCodeBase64 = qrCodeService.generateReservationQRContentJSON(reservation);

        NotificationEntity notification = NotificationEntity.builder()
                .user(reservation.getUser())
                .reservation(reservation)
                .message("Your reservation with ID" + reservation.getId() + "has been created")
                .type(NotificationType.RESERVATION_PAID)
                .status(NotificationStatus.UNREAD)
                .build();

        notificationService.createNotification(notification);

        emailService.createEmailOutbox(reservation.getUser(), reservation, notification, qrCodeBase64, null);
    }

    private void handlePaymentIntentFailed(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new IllegalStateException("PaymentIntent not found"));

        log.warn("Payment failed: {}", paymentIntent.getId());

        PaymentEntity payment = paymentRepository
                .findByStripePaymentIntentId(paymentIntent.getId())
                .orElseThrow(() -> new PaymentNotFoundException(paymentIntent.getId()));

        if (payment.getStatus() == PaymentStatus.FAILED) {
            return;
        }

        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailedAt(LocalDateTime.now());
        payment.setWebhookReceivedAt(LocalDateTime.now());
        payment.setFailureMessage(
                paymentIntent.getLastPaymentError() != null
                        ? paymentIntent.getLastPaymentError().getMessage()
                        : "Unknown error"
        );

        paymentRepository.save(payment);

        ReservationEntity reservation = payment.getReservation();
        reservation.setBookingStatus(BookingStatus.PAYMENT_FAILED);
        reservationService.updateReservation(reservation);

        log.info("Payment marked as failed");
    }

    private void handlePaymentIntentRequiresAction(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow();

        log.info("Payment requires action (3DS): {}", paymentIntent.getId());

        PaymentEntity payment = paymentRepository
                .findByStripePaymentIntentId(paymentIntent.getId())
                .orElseThrow(() -> new PaymentNotFoundException(paymentIntent.getId()));

        payment.setStatus(PaymentStatus.REQUIRES_ACTION);
        payment.setWebhookReceivedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    private void handleChargeRefunded(Event event) {
        Charge charge = (Charge) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow();

        log.info("Charge refunded: {}", charge.getId());

        PaymentEntity payment = paymentRepository
                .findByStripeChargeId(charge.getId())
                .orElseThrow(() -> new PaymentNotFoundException(charge.getId()));

        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        ReservationEntity reservation = payment.getReservation();
        reservation.setBookingStatus(BookingStatus.REFUNDED);
        reservationService.updateReservation(reservation);
    }

    @Override
    public PaymentResponse getPaymentStatus(Long paymentId) {
        log.info("Getting payment status for: {}", paymentId);

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        return mapToResponse(payment);
    }

    /**
     * Reembolsa un pago
     */
    @Transactional
    @Override
    public void refundPayment(Long paymentId, BigDecimal amount) {
        log.info("Refunding payment: {}, amount: {}", paymentId, amount);

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Can only refund succeeded payments");
        }

        try {
            RefundCreateParams.Builder paramsBuilder = RefundCreateParams.builder()
                    .setPaymentIntent(payment.getStripePaymentIntentId());

            // Si se especifica monto, reembolso parcial
            if (amount != null) {
                paramsBuilder.setAmount(toStripeAmount(amount));
            }
            // Si no, reembolso completo

            RefundCreateParams params = paramsBuilder.build();
            Refund refund = Refund.create(params);

            log.info("✅ Refund created: {}", refund.getId());

            // El webhook actualizará el estado automáticamente

        } catch (StripeException e) {
            log.error("Error creating refund", e);
            throw new PaymentProcessingException("Failed to refund payment: " + e.getMessage(), e);
        }
    }

    @Override
    public void initiateRefund(Long reservationId, BigDecimal amount) {
        log.info("Initiating refund for reservation: {}, amount: {}", reservationId, amount);

        Optional<PaymentEntity> payment = paymentRepository
                .findSuccessfulPaymentByReservation(reservationId);

        if (payment.isEmpty()) {
            log.warn("⚠️ No successful payment found for reservation: {}", reservationId);
            return;
        }

        refundPayment(payment.get().getId(), amount);
    }

    private Long toStripeAmount(BigDecimal amount) {
        return amount.multiply(new BigDecimal("100")).longValue();
    }

    private PaymentResponse mapToResponse(PaymentEntity payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .stripePaymentIntentId(payment.getStripePaymentIntentId())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}

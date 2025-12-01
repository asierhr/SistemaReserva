package com.asier.SistemaReservas.payment.service;

import com.asier.SistemaReservas.payment.domain.records.PaymentResponse;
import com.asier.SistemaReservas.payment.domain.records.CreatePaymentRequest;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse createPayment(CreatePaymentRequest request);
    void processWebhook(String payload, String signatureHeader);
    PaymentResponse getPaymentStatus(Long paymentId);
    void refundPayment(Long paymentId, BigDecimal amount);
    void initiateRefund(Long reservationId, BigDecimal amount);
}

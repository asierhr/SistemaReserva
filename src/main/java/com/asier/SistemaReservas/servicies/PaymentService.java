package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.PaymentDTO;
import com.asier.SistemaReservas.domain.records.CreatePaymentRequest;
import com.asier.SistemaReservas.domain.records.PaymentResponse;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse createPayment(CreatePaymentRequest request);
    void processWebhook(String payload, String signatureHeader);
    PaymentResponse getPaymentStatus(Long paymentId);
    void refundPayment(Long paymentId, BigDecimal amount);
    void initiateRefund(Long reservationId, BigDecimal amount);
}

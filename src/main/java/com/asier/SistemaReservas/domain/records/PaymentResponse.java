package com.asier.SistemaReservas.domain.records;

import com.asier.SistemaReservas.domain.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(
        Long paymentId,
        String stripePaymentIntentId,
        String clientSecret,
        PaymentStatus status,
        BigDecimal amount,
        String currency,
        LocalDateTime createdAt
) {
}

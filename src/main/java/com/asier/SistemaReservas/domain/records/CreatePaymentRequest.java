package com.asier.SistemaReservas.domain.records;

import java.math.BigDecimal;
import java.util.Map;

public record CreatePaymentRequest(
        Long reservationId,
        BigDecimal amount,
        String currency,
        String customerEmail,
        Map<String, String> metadata
        ) {
}

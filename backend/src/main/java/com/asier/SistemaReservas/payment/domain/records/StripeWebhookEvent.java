package com.asier.SistemaReservas.payment.domain.records;

import java.util.Map;

public record StripeWebhookEvent(
        String id,
        String type,
        Map<String, Object> data
) {
}

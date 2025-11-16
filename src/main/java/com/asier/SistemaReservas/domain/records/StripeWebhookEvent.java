package com.asier.SistemaReservas.domain.records;

import java.util.Map;

public record StripeWebhookEvent(
        String id,
        String type,
        Map<String, Object> data
) {
}

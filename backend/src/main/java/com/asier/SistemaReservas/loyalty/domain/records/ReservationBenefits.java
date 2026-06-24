package com.asier.SistemaReservas.loyalty.domain.records;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ReservationBenefits(
        String tierName,
        BigDecimal originalPrice,
        BigDecimal discountPercentage,
        BigDecimal discountAmount,
        BigDecimal finalPrice,
        boolean flexibleCancellation,
        boolean freeCancellation,
        boolean priorityBooking,
        boolean exclusiveOffers,
        boolean priorityCheckIn,
        boolean exclusiveExperiences,
        List<String>appliedBenefits
) {
}

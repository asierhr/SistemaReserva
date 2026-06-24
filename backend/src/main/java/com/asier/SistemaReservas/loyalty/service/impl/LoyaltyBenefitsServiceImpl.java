package com.asier.SistemaReservas.loyalty.service.impl;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.domain.records.ReservationBenefits;
import com.asier.SistemaReservas.loyalty.service.LoyaltyBenefitsService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoyaltyBenefitsServiceImpl implements LoyaltyBenefitsService {
    private final LoyaltyService loyaltyService;

    @Override
    public ReservationBenefits applyBenefits(UserEntity user, BigDecimal originalPrice) {
        LoyaltyTierEntity tier = loyaltyService.getLoyaltyByUser(user.getId()).getLoyaltyTier();
        BigDecimal discount = calculateDiscount(tier, originalPrice);
        BigDecimal finalPrice = originalPrice.subtract(discount);

        boolean flexibleCancellation = hasFlexibleCancellation(tier);
        boolean freeCancellation = hasFreeCancellation(tier);
        boolean priorityBooking = hasPriorityBooking(tier);
        boolean exclusiveOffers = hasExclusiveOffers(tier);
        boolean priorityCheckIn = hasPriorityCheckIn(tier);
        boolean exclusiveExperiences = hasExclusiveExperiences(tier);

        return ReservationBenefits.builder()
                .tierName(tier.getName())
                .originalPrice(originalPrice)
                .discountPercentage(getDiscountPercentage(tier))
                .discountAmount(discount)
                .finalPrice(finalPrice)
                .flexibleCancellation(flexibleCancellation)
                .freeCancellation(freeCancellation)
                .priorityBooking(priorityBooking)
                .exclusiveOffers(exclusiveOffers)
                .priorityCheckIn(priorityCheckIn)
                .exclusiveExperiences(exclusiveExperiences)
                .appliedBenefits(tier.getBenefits())
                .build();
    }

    private BigDecimal calculateDiscount(LoyaltyTierEntity tier, BigDecimal originalPrice){
        BigDecimal discountPercentage = getDiscountPercentage(tier);
        return originalPrice.multiply(discountPercentage).divide(new BigDecimal("100"),2, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscountPercentage(LoyaltyTierEntity tier){
        return switch (tier.getName().toLowerCase()){
            case "silver" -> new BigDecimal("10");
            case "gold" -> new BigDecimal("15");
            case "platinum" -> new BigDecimal("20");
            default -> BigDecimal.ZERO;
        };
    }

    private boolean hasFlexibleCancellation(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("flexible cancellation"));
    }

    private boolean hasFreeCancellation(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("free cancellation"));
    }


    private boolean hasPriorityBooking(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("booking priority"));
    }


    private boolean hasExclusiveOffers(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("exclusive offers"));
    }


    private boolean hasPriorityCheckIn(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("priority in check"));
    }


    private boolean hasExclusiveExperiences(LoyaltyTierEntity tier) {
        return tier.getBenefits().stream()
                .anyMatch(b -> b.toLowerCase().contains("exclusive experiences"));
    }

    @Override
    public LocalDateTime getCancellationDeadline(LoyaltyTierEntity tier, LocalDateTime reservationDate) {
        if (hasFreeCancellation(tier)) {
            return reservationDate.minusHours(24);
        } else if (hasFlexibleCancellation(tier)) {
            return reservationDate.minusHours(72);
        } else {
            return reservationDate.minusDays(7);
        }
    }

    @Override
    public BigDecimal getCancellationFee(LoyaltyTierEntity tier, BigDecimal totalPrice, LocalDateTime now, LocalDateTime deadline, LocalDateTime reservationDate) {
        if(reservationDate.plusDays(1).isAfter(now) || hasFreeCancellation(tier)){
            return BigDecimal.ZERO;
        }
        if (now.isAfter(deadline)) {
            return totalPrice;
        }
        if (hasFlexibleCancellation(tier)) {
            return totalPrice.multiply(new BigDecimal("0.25"));
        }
        return totalPrice.multiply(new BigDecimal("0.50"));
    }

}

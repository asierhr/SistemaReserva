package com.asier.SistemaReservas.loyalty.service;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.domain.records.ReservationBenefits;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface LoyaltyBenefitsService {
    ReservationBenefits applyBenefits(UserEntity user, BigDecimal originalPrice);
    LocalDateTime getCancellationDeadline(LoyaltyTierEntity tier, LocalDateTime reservationDate);
    BigDecimal getCancellationFee(LoyaltyTierEntity tier, BigDecimal totalPrice, LocalDateTime now, LocalDateTime deadline, LocalDateTime reservationDate);
}

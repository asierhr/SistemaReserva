package com.asier.SistemaReservas.loyalty.service;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;

public interface LoyaltyTierService {
    LoyaltyTierEntity getTier(String name);
    LoyaltyTierEntity getTierByPoints(int points);
}

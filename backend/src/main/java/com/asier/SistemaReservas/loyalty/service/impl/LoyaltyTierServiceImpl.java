package com.asier.SistemaReservas.loyalty.service.impl;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.repository.LoyaltyTierRepository;
import com.asier.SistemaReservas.loyalty.service.LoyaltyTierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoyaltyTierServiceImpl implements LoyaltyTierService {
    private final LoyaltyTierRepository loyaltyTierRepository;

    @Override
    public LoyaltyTierEntity getTier(String name) {
        return loyaltyTierRepository.findByName(name);
    }

    @Override
    public LoyaltyTierEntity getTierByPoints(int points) {
        return loyaltyTierRepository.findTierByPoints(points);
    }
}

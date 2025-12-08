package com.asier.SistemaReservas.loyalty.service.impl;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyPointsEntity;
import com.asier.SistemaReservas.loyalty.repository.LoyaltyRepository;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyTierService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {
    private final UserService userService;
    private final LoyaltyTierService loyaltyTierService;
    private final LoyaltyRepository loyaltyRepository;

    @Override
    public void createLoyaltyUser(UserEntity user) {
        LoyaltyPointsEntity loyaltyPoints = LoyaltyPointsEntity.builder()
                .points(0)
                .user(user)
                .loyaltyTier(loyaltyTierService.getTier("Bronze"))
                .build();
        loyaltyRepository.save(loyaltyPoints);
    }

    @Override
    public void updateLoyalty(Long userId, int points) {
        LoyaltyPointsEntity loyaltyTier = loyaltyRepository.findByUserId(userService.getUserEntity().getId());
        if(loyaltyTier != null) createLoyaltyUser(userService.getUserEntity());
        int newPoints = loyaltyTier.getPoints() + points;
        if(newPoints > loyaltyTier.getLoyaltyTier().getMaxPoints()){
            loyaltyTier.setLoyaltyTier(loyaltyTierService.getTierByPoints(newPoints));
        }
        loyaltyTier.setPoints(newPoints);
        loyaltyRepository.save(loyaltyTier);
    }

    @Override
    public LoyaltyPointsEntity getLoyaltyByUser(Long userId) {
        return loyaltyRepository.findByUserId(userId);
    }
}

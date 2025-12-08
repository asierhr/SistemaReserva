package com.asier.SistemaReservas.loyalty.service;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyPointsEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;

public interface LoyaltyService {
    void createLoyaltyUser(UserEntity user);
    void updateLoyalty(Long userId,int points);
    LoyaltyPointsEntity getLoyaltyByUser(Long userId);
}

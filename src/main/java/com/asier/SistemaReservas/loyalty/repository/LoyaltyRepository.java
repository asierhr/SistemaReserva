package com.asier.SistemaReservas.loyalty.repository;

import com.asier.SistemaReservas.loyalty.domain.LoyaltyPointsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoyaltyRepository extends JpaRepository<LoyaltyPointsEntity, Long> {
    @Query("SELECT l FROM LoyaltyPointsEntity l where l.user.id = :id")
    LoyaltyPointsEntity findByUserId(@Param("id") Long id);
}

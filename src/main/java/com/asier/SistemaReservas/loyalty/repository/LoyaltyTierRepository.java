package com.asier.SistemaReservas.loyalty.repository;

import com.asier.SistemaReservas.loyalty.domain.LoyaltyTierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoyaltyTierRepository extends JpaRepository<LoyaltyTierEntity, Long>{
    LoyaltyTierEntity findByName(String name);
    @Query("SELECT l FROM LoyaltyTierEntity l where :points BETWEEN l.minPoints AND l.maxPoints")
    LoyaltyTierEntity findTierByPoints(@Param("points") int points);
}

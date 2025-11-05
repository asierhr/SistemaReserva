package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelHistoryRepository extends JpaRepository<HotelHistoryEntity, Long> {
    HotelHistoryEntity findByUserId(Long id);
    Boolean existsByUserId(Long id);
}

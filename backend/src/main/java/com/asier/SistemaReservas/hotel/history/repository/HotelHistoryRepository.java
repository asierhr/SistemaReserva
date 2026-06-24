package com.asier.SistemaReservas.hotel.history.repository;

import com.asier.SistemaReservas.hotel.history.domain.entity.HotelHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelHistoryRepository extends JpaRepository<HotelHistoryEntity, Long> {
    HotelHistoryEntity findByUserId(Long id);
    Boolean existsByUserId(Long id);
}

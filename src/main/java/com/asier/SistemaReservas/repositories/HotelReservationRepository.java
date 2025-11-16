package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservationEntity, Long> {
    List<HotelReservationEntity> findAllByUserId(Long id);
}

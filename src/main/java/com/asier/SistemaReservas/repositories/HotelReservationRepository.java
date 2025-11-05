package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservationEntity, Long> {
}

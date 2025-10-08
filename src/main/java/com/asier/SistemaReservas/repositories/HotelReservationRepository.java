package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReservationRepository extends JpaRepository<HotelReservationEntity, Long> {
}

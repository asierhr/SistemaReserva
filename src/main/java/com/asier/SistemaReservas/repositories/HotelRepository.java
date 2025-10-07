package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}

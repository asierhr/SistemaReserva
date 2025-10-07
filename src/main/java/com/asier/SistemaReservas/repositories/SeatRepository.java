package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllByFlightId(Long flightId);
}

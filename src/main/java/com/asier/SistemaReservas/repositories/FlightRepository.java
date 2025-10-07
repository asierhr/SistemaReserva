package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
}

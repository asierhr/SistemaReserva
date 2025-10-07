package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightReservationRepository extends JpaRepository<FlightReservationEntity, Long> {
}

package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservationEntity, Long> {
}

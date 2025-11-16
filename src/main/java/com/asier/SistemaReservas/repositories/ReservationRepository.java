package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}

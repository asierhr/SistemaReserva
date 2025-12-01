package com.asier.SistemaReservas.reservation.repository;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}

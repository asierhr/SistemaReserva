package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllByFlightId(Long flightId);
    List<SeatEntity> findAllByFlightIdAndAvailableTrue(Long flightId);
}

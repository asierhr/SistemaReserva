package com.asier.SistemaReservas.seats.repository;

import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllByFlightId(Long flightId);
    List<SeatEntity> findAllByFlightIdAndAvailableTrue(Long flightId);
}

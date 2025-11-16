package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservationEntity, Long> {
    @Query("""
        SELECT fr FROM FlightReservationEntity fr
        WHERE fr.bookingStatus = 'PENDING_PAYMENT'
          AND fr.expiresAt < :now
    """)
    List<FlightReservationEntity> findExpiredPendingReservations(@Param("now") LocalDateTime now);
}

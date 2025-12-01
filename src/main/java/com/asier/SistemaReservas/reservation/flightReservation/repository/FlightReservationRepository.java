package com.asier.SistemaReservas.reservation.flightReservation.repository;

import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    List<FlightReservationEntity> findAllByUserId(Long id);

    @Query("""
        SELECT fr FROM FlightReservationEntity fr
        WHERE fr.checkedIn = false
        AND (fr.flight.flightDay = :day AND fr.flight.departureTime < :now)
    """
    )
    List<FlightReservationEntity> findAllByCheckInFalseAndFlightDone(@Param("day") LocalDate day, @Param("now") LocalDateTime now);
}

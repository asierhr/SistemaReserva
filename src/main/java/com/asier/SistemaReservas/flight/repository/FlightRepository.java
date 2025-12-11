package com.asier.SistemaReservas.flight.repository;

import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    @Query("SELECT f FROM FlightEntity f WHERE f.origin.id = :originId AND f.flightDay = :flightDay")
    List<FlightEntity> getFlightsByFlightSearch(
            @Param("originId") Long originId,
            @Param("flightDay") LocalDate flightDay
    );

    @Query("SELECT DISTINCT f.origin.location.city FROM FlightEntity f")
    List<String> findAllOrigins();

    @Query("SELECT DISTINCT f.destination.location.city FROM FlightEntity f")
    List<String> findAllDestinations();

    @Query("""
    SELECT EXISTS (
        SELECT 1 FROM FlightEntity f 
        WHERE f.flightNumber = :flightNumber 
        AND f.airline.id = :airlineId 
        AND f.flightDay = :flightDay
    )
    """)
    boolean existsFlight(
            @Param("flightNumber") String flightNumber,
            @Param("airlineId") Long airlineId,
            @Param("flightDay") LocalDate flightDay
    );
}

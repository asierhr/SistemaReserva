package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    @Query("SELECT f FROM FlightEntity f where f.origin = :origin AND f.destination = :destination AND f.flightDay = :flightDay")
    List<FlightEntity> getFlightsByFlightSearch(@Param("origin") String origin, @Param("destination") String destination, @Param("flightDay") LocalDate flight);

    @Query("SELECT DISTINCT f.origin FROM FlightEntity f")
    List<String> findAllOrigins();

    @Query("SELECT DISTINCT f.destination FROM FlightEntity f")
    List<String> findAllDestinations();
}

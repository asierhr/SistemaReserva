package com.asier.SistemaReservas.aiport.repository;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {
    Optional<AirportEntity> findByAirportName(String name);
    @Query("SELECT a FROM AirportEntity a WHERE a.location.city = :city")
    AirportEntity findByLocationCity(@Param("city") String city);

}

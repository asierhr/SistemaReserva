package com.asier.SistemaReservas.search.flightSearch.repository;

import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSearchRepository extends JpaRepository<FlightSearchEntity,Long>{
    @Query("SELECT f FROM FlightSearchEntity f where f.user.id = :id")
    List<FlightSearchEntity> findAllByUserId(@Param("id") Long userId);
}

package com.asier.SistemaReservas.airline.repository;

import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<AirlineEntity,Long> {
}

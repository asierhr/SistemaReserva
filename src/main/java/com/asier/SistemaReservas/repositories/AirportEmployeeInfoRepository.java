package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.AirportEmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportEmployeeInfoRepository extends JpaRepository<AirportEmployeeInfoEntity, Long> {
    AirportEmployeeInfoEntity findByUserId(Long id);
}

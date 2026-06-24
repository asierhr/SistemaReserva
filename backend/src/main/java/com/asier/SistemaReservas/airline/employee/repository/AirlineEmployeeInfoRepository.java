package com.asier.SistemaReservas.airline.employee.repository;

import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineEmployeeInfoRepository extends JpaRepository<AirlineEmployeeInfoEntity, Long> {
    AirlineEmployeeInfoEntity findByUserId(Long id);
}

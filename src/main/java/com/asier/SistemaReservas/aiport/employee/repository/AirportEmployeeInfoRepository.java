package com.asier.SistemaReservas.aiport.employee.repository;

import com.asier.SistemaReservas.aiport.employee.domain.entity.AirportEmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportEmployeeInfoRepository extends JpaRepository<AirportEmployeeInfoEntity, Long> {
    AirportEmployeeInfoEntity findByUserId(Long id);
}

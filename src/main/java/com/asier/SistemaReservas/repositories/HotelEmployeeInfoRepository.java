package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelEmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelEmployeeInfoRepository extends JpaRepository<HotelEmployeeInfoEntity, Long> {
}

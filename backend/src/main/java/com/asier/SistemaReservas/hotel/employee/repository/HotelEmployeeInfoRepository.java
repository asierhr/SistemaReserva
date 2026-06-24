package com.asier.SistemaReservas.hotel.employee.repository;

import com.asier.SistemaReservas.hotel.employee.domain.entity.HotelEmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelEmployeeInfoRepository extends JpaRepository<HotelEmployeeInfoEntity, Long> {
}

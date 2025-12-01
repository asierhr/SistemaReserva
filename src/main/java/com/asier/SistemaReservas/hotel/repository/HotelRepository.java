package com.asier.SistemaReservas.hotel.repository;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @Query("SELECT DISTINCT h.location.city FROM HotelEntity h")
    List<String> findAllCities();
}

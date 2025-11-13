package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @Query("SELECT DISTINCT h.location.city FROM HotelEntity h")
    List<String> findAllCities();
}

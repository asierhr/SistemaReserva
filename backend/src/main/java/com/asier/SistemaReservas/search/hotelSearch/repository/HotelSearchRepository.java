package com.asier.SistemaReservas.search.hotelSearch.repository;

import com.asier.SistemaReservas.search.hotelSearch.domain.entity.HotelSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelSearchRepository extends JpaRepository<HotelSearchEntity,Long> {
    @Query("SELECT h FROM HotelSearchEntity h where h.user.id = :id")
    List<HotelSearchEntity> findAllByUserId(@Param("id") Long UserId);
}

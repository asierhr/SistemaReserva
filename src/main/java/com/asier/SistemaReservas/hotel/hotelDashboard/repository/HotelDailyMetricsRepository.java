package com.asier.SistemaReservas.hotel.hotelDashboard.repository;

import com.asier.SistemaReservas.hotel.hotelDashboard.domain.entity.HotelDailyMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelDailyMetricsRepository extends JpaRepository<HotelDailyMetricsEntity,Long> {
    @Query("SELECT h FROM HotelDailyMetricsEntity h where h.hotel.id = :hotelId AND h.date = :now")
    HotelDailyMetricsEntity findByHotelId(@Param("hotelId") Long hotelId, @Param("now") LocalDate now);

    @Query("SELECT h FROM HotelDailyMetricsEntity h where h.hotel.id =:hotelId AND h.date BETWEEN :days AND :now")
    List<HotelDailyMetricsEntity> findLast30DaysMetrics(@Param("hotelId") Long hotelId, @Param("now") LocalDate now, @Param("days") LocalDate days);
}

package com.asier.SistemaReservas.hotel.hotelDashboard.service;

import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDailyMetricsDTO;
import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDashboardDTO;

import java.util.List;

public interface HotelDailyMetricsService {
    void createDailyMetric(Long hotelId);
    HotelDailyMetricsDTO getDailyMetric(Long hotelId);
    List<HotelDailyMetricsDTO> getLast30DaysMetrics(Long hotelId);
    HotelDashboardDTO getDashboard(Long hotelId);
    void updateBookingMetrics(Long hotelId, Long reservationId);
    void updateCommentMetrics(Long hotelId);
    void updateCancellationMetrics(Long hotelId);
    void updateTotalSearches(Long hotelId);
}

package com.asier.SistemaReservas.hotel.hotelDashboard.controller;

import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDashboardDTO;
import com.asier.SistemaReservas.hotel.hotelDashboard.service.HotelDailyMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelDashboardController {
    private final HotelDailyMetricsService hotelDailyMetricsService;

    @GetMapping(path = "/hotel/{id}/dashboard")
    public HotelDashboardDTO getDashboard(@PathVariable Long id){
        return hotelDailyMetricsService.getDashboard(id);
    }
}

package com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class HotelDashboardDTO {
    private List<HotelDailyMetricsDTO> historicalMetrics;
    private HotelDailyMetricsDTO realTimeMetrics;
    private LocalDateTime createdAt;
}

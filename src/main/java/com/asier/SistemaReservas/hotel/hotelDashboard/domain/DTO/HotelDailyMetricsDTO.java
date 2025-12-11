package com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class HotelDailyMetricsDTO {
    private LocalDate date;
    private Double occupancyRate;
    private BigDecimal dailyRevenue;
    private Integer totalRooms;
    private Integer occupiedRooms;
    private Integer availableRooms;
    private Integer checkIns;
    private Integer checkOuts;
    private Integer totalBookings;
    private Integer totalSearches;
    private Integer cancellations;
    private Double averageRating;
}

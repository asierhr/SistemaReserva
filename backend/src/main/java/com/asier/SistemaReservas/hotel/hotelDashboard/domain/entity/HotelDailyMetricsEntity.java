package com.asier.SistemaReservas.hotel.hotelDashboard.domain.entity;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotelDailyMetrics")
public class HotelDailyMetricsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double occupancyRate;

    @Column(nullable = false)
    private BigDecimal dailyRevenue;

    @Column(nullable = false)
    private Integer totalRooms;

    @Column(nullable = false)
    private Integer occupiedRooms;

    @Column(nullable = false)
    private Integer availableRooms;

    @Column(nullable = false)
    private Integer checkIns;

    @Column(nullable = false)
    private Integer checkOuts;

    @Column(nullable = false)
    private Integer totalBookings;

    @Column(nullable = false)
    private Integer totalSearches;

    @Column(nullable = false)
    private Integer cancellations;

    @Column(nullable = false)
    private Double averageRating;
}

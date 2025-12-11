package com.asier.SistemaReservas.combination.domain.entity;

import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "combination")
public class CombinationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "combination_outbound_flights",
            joinColumns = @JoinColumn(name = "combination_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<FlightEntity> outboundFlights;

    @ManyToMany
    @JoinTable(
            name = "combination_inbound_flights",
            joinColumns = @JoinColumn(name = "combination_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<FlightEntity> inboundFlights;

    @ManyToOne
    private HotelEntity hotel;

    private BigDecimal totalPrice;
}

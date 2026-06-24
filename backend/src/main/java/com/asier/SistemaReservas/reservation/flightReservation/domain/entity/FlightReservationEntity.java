package com.asier.SistemaReservas.reservation.flightReservation.domain.entity;

import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flightReservations")
public class FlightReservationEntity extends ReservationEntity {
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatEntity> seat;

    private LocalDateTime expiresAt;
}

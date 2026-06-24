package com.asier.SistemaReservas.seats.domain.entity;

import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatType;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seats")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private FlightReservationEntity reservation;

    @Column(nullable = false)
    private BigDecimal costPerSeat;

    @Column(nullable = false)
    private String seatNumber;
    @Column(nullable = false)
    private Integer seatRow;
    @Column(nullable = false)
    private String seatColumn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClass seatClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @Column(nullable = false)
    private boolean available = true;

    @PrePersist
    @PreUpdate
    private void generateSeatNumber(){
        this.seatNumber = seatRow + seatColumn;
    }
}

package com.asier.SistemaReservas.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "hotelReservations")
@DiscriminatorValue("HOTEL")
public class HotelReservationEntity extends ReservationEntity {
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> room;

    private LocalDate checkIn;
    private LocalDate checkOut;
}

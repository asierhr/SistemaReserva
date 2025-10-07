package com.asier.SistemaReservas.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotelReservations")
@DiscriminatorValue("HOTEL")
public class HotelReservationEntity extends ReservationEntity {
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @OneToMany
    @JoinColumn(name = "room_id")
    private List<RoomEntity> room = new ArrayList<>();

    private LocalDate checkIn;
    private LocalDate checkOut;
}

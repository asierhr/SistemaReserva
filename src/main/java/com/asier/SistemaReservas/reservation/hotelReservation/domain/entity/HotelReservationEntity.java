package com.asier.SistemaReservas.reservation.hotelReservation.domain.entity;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.room.domain.entity.RoomReservationEntity;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "hotelReservations")
@DiscriminatorValue("HOTEL")
public class HotelReservationEntity extends ReservationEntity {
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomReservationEntity> rooms = new ArrayList<>();

    private LocalDate checkIn;
    private LocalDate checkOut;
}

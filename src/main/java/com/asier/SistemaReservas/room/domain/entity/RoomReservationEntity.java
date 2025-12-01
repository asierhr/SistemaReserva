package com.asier.SistemaReservas.room.domain.entity;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_reservations")
@Data
public class RoomReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private HotelReservationEntity reservation;

}

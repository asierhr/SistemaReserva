package com.asier.SistemaReservas.domain.entities;

import com.asier.SistemaReservas.domain.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomReservationEntity> reservations = new ArrayList<>();

    @Column(nullable = false)
    private String numRoom;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    private BigDecimal costPerNight;

    @Column(nullable = false)
    private boolean available;

}

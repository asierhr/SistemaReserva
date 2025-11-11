package com.asier.SistemaReservas.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotelFavourites")
public class HotelFavouriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "hotel_favourite_hotels",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    List<HotelEntity> hotels = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}

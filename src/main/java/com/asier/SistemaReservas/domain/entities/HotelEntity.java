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
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hotelName;

    @Embedded
    private Location location;

    private Double rating;

    @Column(nullable = false)
    private String stars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomEntity> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "hotels", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelHistoryEntity> hotelHistories = new ArrayList<>();

    @ManyToMany(mappedBy = "hotels", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelFavouriteEntity> hotelFavourites = new ArrayList<>();

    @OneToMany(mappedBy = "hotel" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HotelEmployeeInfoEntity> hotelEmployees = new ArrayList<>();


}


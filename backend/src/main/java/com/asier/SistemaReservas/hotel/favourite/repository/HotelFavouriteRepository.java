package com.asier.SistemaReservas.hotel.favourite.repository;

import com.asier.SistemaReservas.hotel.favourite.domain.entity.HotelFavouriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelFavouriteRepository extends JpaRepository<HotelFavouriteEntity, Long> {
    HotelFavouriteEntity findByUserId(Long id);
    Boolean existsByUserId(Long id);
}

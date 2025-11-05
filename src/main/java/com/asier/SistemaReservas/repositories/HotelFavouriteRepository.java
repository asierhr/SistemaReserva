package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.HotelFavouriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelFavouriteRepository extends JpaRepository<HotelFavouriteEntity, Long> {
    HotelFavouriteEntity findByUserId(Long id);
    Boolean existsByUserId(Long id);
}

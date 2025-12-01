package com.asier.SistemaReservas.hotel.favourite.service;


import com.asier.SistemaReservas.hotel.favourite.domain.DTO.HotelFavouriteDTO;
import com.asier.SistemaReservas.hotel.favourite.domain.entity.HotelFavouriteEntity;

public interface HotelFavouriteService {
    HotelFavouriteEntity createFavourites();
    HotelFavouriteDTO updateFavourites(Long hotelId);
    HotelFavouriteDTO getAllFavourites();
}

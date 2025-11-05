package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.HotelFavouriteDTO;
import com.asier.SistemaReservas.domain.entities.HotelFavouriteEntity;


public interface HotelFavouriteService {
    HotelFavouriteEntity createFavourites();
    HotelFavouriteDTO updateFavourites(Long hotelId);
    HotelFavouriteDTO getAllFavourites();
}

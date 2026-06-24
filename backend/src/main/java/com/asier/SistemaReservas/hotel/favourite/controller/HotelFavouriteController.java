package com.asier.SistemaReservas.hotel.favourite.controller;

import com.asier.SistemaReservas.hotel.favourite.domain.DTO.HotelFavouriteDTO;
import com.asier.SistemaReservas.hotel.favourite.service.HotelFavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelFavouriteController {
    private final HotelFavouriteService hotelFavouriteService;

    @PostMapping(path = "/hotels/favourites/{hotelId}")
    public HotelFavouriteDTO updateHotelHistory(@PathVariable Long hotelId){
        return hotelFavouriteService.updateFavourites(hotelId);
    }

    @GetMapping(path = "/users/hotels/favourites")
    public HotelFavouriteDTO  getAllFavourites(){
        return hotelFavouriteService.getAllFavourites();
    }
}

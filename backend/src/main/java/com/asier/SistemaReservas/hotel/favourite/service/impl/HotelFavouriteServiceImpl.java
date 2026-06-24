package com.asier.SistemaReservas.hotel.favourite.service.impl;

import com.asier.SistemaReservas.hotel.favourite.domain.DTO.HotelFavouriteDTO;
import com.asier.SistemaReservas.hotel.favourite.domain.entity.HotelFavouriteEntity;
import com.asier.SistemaReservas.hotel.favourite.mapper.HotelFavouriteMapper;
import com.asier.SistemaReservas.hotel.favourite.repository.HotelFavouriteRepository;
import com.asier.SistemaReservas.hotel.favourite.service.HotelFavouriteService;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HotelFavouriteServiceImpl implements HotelFavouriteService {
    private final HotelFavouriteRepository hotelFavouriteRepository;
    private final HotelFavouriteMapper hotelFavouriteMapper;
    private final HotelService hotelService;
    private final UserService userService;

    @Override
    public HotelFavouriteEntity createFavourites() {
        UserEntity user = userService.getUserEntity();
        HotelFavouriteEntity hotelFavourite = new HotelFavouriteEntity();
        hotelFavourite.setUser(user);
        return hotelFavouriteRepository.save(hotelFavourite);
    }

    @Override
    public HotelFavouriteDTO updateFavourites(Long hotelId) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        UserEntity user = userService.getUserEntity();
        HotelFavouriteEntity hotelFavourite;
        if(!hotelFavouriteRepository.existsByUserId(user.getId())) hotelFavourite = createFavourites();
        else hotelFavourite = hotelFavouriteRepository.findByUserId(user.getId());
        hotelFavourite.getHotels().add(0,hotelService.getHotelEntity(hotelId));
        return hotelFavouriteMapper.toDTO(hotelFavouriteRepository.save(hotelFavourite));
    }

    @Override
    public HotelFavouriteDTO getAllFavourites() {
        UserEntity user = userService.getUserEntity();
        return hotelFavouriteMapper.toDTO(hotelFavouriteRepository.findByUserId(user.getId()));
    }
}

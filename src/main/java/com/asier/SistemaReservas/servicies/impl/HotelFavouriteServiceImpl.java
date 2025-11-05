package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.HotelFavouriteDTO;
import com.asier.SistemaReservas.domain.entities.HotelFavouriteEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.mapper.HotelFavouriteMapper;
import com.asier.SistemaReservas.repositories.HotelFavouriteRepository;
import com.asier.SistemaReservas.servicies.HotelFavouriteService;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
//
    @Override
    public HotelFavouriteDTO updateFavourites(Long hotelId) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        UserEntity user = userService.getUserEntity();
        HotelFavouriteEntity hotelFavourite;
        if(!hotelFavouriteRepository.existsByUserId(user.getId())) hotelFavourite = createFavourites();
        else hotelFavourite = hotelFavouriteRepository.findByUserId(user.getId());
        hotelFavourite.getHotels().addFirst(hotelService.getHotelEntity(hotelId));
        return hotelFavouriteMapper.toDTO(hotelFavouriteRepository.save(hotelFavourite));
    }

    @Override
    public HotelFavouriteDTO getAllFavourites() {
        UserEntity user = userService.getUserEntity();
        return hotelFavouriteMapper.toDTO(hotelFavouriteRepository.findByUserId(user.getId()));
    }
}

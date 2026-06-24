package com.asier.SistemaReservas.hotel.favourite.mapper;

import com.asier.SistemaReservas.hotel.favourite.domain.DTO.HotelFavouriteDTO;
import com.asier.SistemaReservas.hotel.favourite.domain.entity.HotelFavouriteEntity;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface HotelFavouriteMapper {
    HotelFavouriteDTO toDTO(HotelFavouriteEntity hotelFavourite);
    List<HotelFavouriteDTO> toDTOList(List<HotelFavouriteEntity> hotelFavourites);
}

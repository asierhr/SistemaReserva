package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelFavouriteDTO;
import com.asier.SistemaReservas.domain.entities.HotelFavouriteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface HotelFavouriteMapper {
    HotelFavouriteDTO toDTO(HotelFavouriteEntity hotelFavourite);
    List<HotelFavouriteDTO> toDTOList(List<HotelFavouriteEntity> hotelFavourites);
}

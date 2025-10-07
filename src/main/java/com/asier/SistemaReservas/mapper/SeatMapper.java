package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(source = "seatClass", target = "seatClass")
    @Mapping(source = "seatType", target = "seatType")
    @Mapping(source = "flight.id", target = "flightId")
    SeatDTO toDTO(SeatEntity seat);
    List<SeatDTO> toDTOList(List<SeatEntity> seats);
    List<SeatEntity> toEntityList(List<SeatDTO> seats);
}

package com.asier.SistemaReservas.hotel.hotelDashboard.mapper;

import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDailyMetricsDTO;
import com.asier.SistemaReservas.hotel.hotelDashboard.domain.entity.HotelDailyMetricsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelDailyMetricsMapper {
    HotelDailyMetricsDTO toDTO(HotelDailyMetricsEntity hotelDailyMetrics);
    HotelDailyMetricsEntity toEntity(HotelDailyMetricsDTO hotelDailyMetricsDTO);
    List<HotelDailyMetricsDTO> toDTOList(List<HotelDailyMetricsEntity> hotelDailyMetrics);
}

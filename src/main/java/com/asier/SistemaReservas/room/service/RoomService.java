package com.asier.SistemaReservas.room.service;

import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.enums.RoomType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoomService {
    Map<RoomType, List<RoomDTO>> getRooms(Long id);
    List<RoomEntity> getRoomsFromIds(List<Long> id);
    Set<List<RoomDTO>> getRoomsBySearch(HotelSearchDTO hotelSearch, String ipAddress);
    List<RoomEntity> findSimilarAvailableRooms(Long id, RoomType type, LocalDate checkIn, LocalDate checkOut);
}

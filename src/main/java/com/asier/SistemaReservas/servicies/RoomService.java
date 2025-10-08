package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.entities.RoomEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;

import java.util.List;
import java.util.Map;

public interface RoomService {
    Map<RoomType, List<RoomDTO>> getRooms(Long id);
    List<RoomEntity> getRoomsFromIds(List<Long> id);
}

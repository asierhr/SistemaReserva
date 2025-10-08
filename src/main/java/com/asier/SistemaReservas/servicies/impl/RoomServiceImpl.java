package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.entities.RoomEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;
import com.asier.SistemaReservas.mapper.RoomMapper;
import com.asier.SistemaReservas.repositories.RoomRepository;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;

    @Override
    public Map<RoomType, List<RoomDTO>> getRooms(Long id) {
        if(!hotelService.existsHotel(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<RoomEntity> roomEntities = roomRepository.findAllByHotelId(id);
        List<RoomDTO> roomDTOS = roomMapper.toDTOList(roomEntities);
        return roomDTOS.stream().collect(Collectors.groupingBy(RoomDTO::getType));
    }

    @Override
    public List<RoomEntity> getRoomsFromIds(List<Long> id) {
        return roomRepository.findAllById(id);
    }
}

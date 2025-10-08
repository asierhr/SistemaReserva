package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.enums.RoomType;
import com.asier.SistemaReservas.servicies.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping(path = "/hotels/{id}/rooms")
    public Map<RoomType, List<RoomDTO>> getRooms(@PathVariable Long id){
        return roomService.getRooms(id);
    }
}

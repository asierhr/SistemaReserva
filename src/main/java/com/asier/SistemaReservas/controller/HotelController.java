package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.HotelDTO;
import com.asier.SistemaReservas.domain.dto.HotelSummaryDTO;
import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.records.HotelSearch;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final RoomService roomService;

    @PostMapping(path = "/hotels")
    public HotelDTO createHotel(@RequestBody HotelDTO hotelDTO){
        return hotelService.createHotel(hotelDTO);
    }

    @GetMapping(path = "/hotels/{id}")
    public HotelSummaryDTO getHotel(@PathVariable Long id){
        return hotelService.getHotel(id);
    }

    @GetMapping(path = "/hotels/search")
    public Set<List<RoomDTO>> getRoomsBySearch(@RequestBody HotelSearch hotelSearch){
        return roomService.getRoomsBySearch(hotelSearch);
    }
}

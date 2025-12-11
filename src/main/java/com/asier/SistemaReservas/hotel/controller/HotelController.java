package com.asier.SistemaReservas.hotel.controller;

import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import com.asier.SistemaReservas.room.service.RoomService;
import com.asier.SistemaReservas.system.IpLocation.service.IpGeolocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final IpGeolocationService ipGeolocationService;

    @PostMapping(path = "/hotels")
    public HotelDTO createHotel(@RequestBody HotelDTO hotelDTO){
        return hotelService.createHotel(hotelDTO);
    }

    @GetMapping(path = "/hotels/{id}")
    public HotelSummaryDTO getHotel(@PathVariable Long id){
        return hotelService.getHotel(id);
    }

    @PostMapping(path = "/hotels/search")
    public Set<List<RoomDTO>> getRoomsBySearch(@RequestBody HotelSearchDTO hotelSearch, HttpServletRequest request){
        String ipAddress = ipGeolocationService.extractIpFromRequest(request);
        return roomService.getRoomsBySearch(hotelSearch, ipAddress);
    }
}

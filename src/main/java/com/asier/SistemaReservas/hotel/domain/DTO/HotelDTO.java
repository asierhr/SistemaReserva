package com.asier.SistemaReservas.hotel.domain.DTO;

import com.asier.SistemaReservas.system.OtherFiles.Location;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDTO {
    private Long id;
    private String hotelName;
    private Location location;
    private String stars;
    private List<RoomDTO> rooms;
}

package com.asier.SistemaReservas.combination.domain.dto;

import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.room.domain.DTO.RoomCombination;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class CombinationDTO {
    private List<FlightPairDTO> flightsPairDTO;
    private Set<RoomCombination> hotels;
}

package com.asier.SistemaReservas.combination.domain.dto;

import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombinationDTO {
    private FlightPairDTO flightPairDTO;
    private HotelSummaryDTO hotelSummaryDTO;
}

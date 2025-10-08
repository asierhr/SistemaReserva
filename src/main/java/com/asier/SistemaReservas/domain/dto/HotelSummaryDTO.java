package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelSummaryDTO {
    private Long id;
    private String hotelName;
    private Location location;
    private String stars;
}

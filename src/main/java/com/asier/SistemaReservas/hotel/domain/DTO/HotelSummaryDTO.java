package com.asier.SistemaReservas.hotel.domain.DTO;

import com.asier.SistemaReservas.system.IpLocation.domain.Location;
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

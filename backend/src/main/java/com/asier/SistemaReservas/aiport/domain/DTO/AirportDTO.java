package com.asier.SistemaReservas.aiport.domain.DTO;

import com.asier.SistemaReservas.system.IpLocation.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportDTO {
    private Long id;
    private Location location;
    private String airportName;
}

package com.asier.SistemaReservas.system.IpLocation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String city;
    private String country;
    private String countryCode;
    private Double latitude;
    private Double longitude;
    private String region;
}

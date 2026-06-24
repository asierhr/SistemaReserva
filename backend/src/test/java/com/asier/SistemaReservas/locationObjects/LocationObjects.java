package com.asier.SistemaReservas.locationObjects;

import com.asier.SistemaReservas.system.IpLocation.domain.Location;

public class LocationObjects {
    public static Location location1(){
        return Location.builder()
                .city("Madrid")
                .country("España")
                .region("Comunidad de Madrid")
                .countryCode("+34")
                .latitude(40.4722)
                .longitude(-3.5608)
                .build();
    }

    public static Location location2(){
        return Location.builder()
                .city("Barcelona")
                .country("Spain")
                .countryCode("ES")
                .latitude(41.2974)
                .longitude(2.0833)
                .region("Cataluña")
                .build();
    }

    public static Location location3(){
        return Location.builder()
                .city("Barcelona")
                .country("Spain")
                .countryCode("ES")
                .latitude(41.3705)
                .longitude(2.1894)
                .region("Cataluña")
                .build();
    }
}

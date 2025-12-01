package com.asier.SistemaReservas.system.OtherFiles;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Location {
    private String city;
    private String country;
    private String direction;
    private String zipCode;
}

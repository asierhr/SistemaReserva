package com.asier.SistemaReservas.aiport.domain.entity;

import com.asier.SistemaReservas.aiport.employee.domain.entity.AirportEmployeeInfoEntity;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.system.OtherFiles.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "airports")
public class AirportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Location location;

    @Column(unique = true)
    private String airportName;

    @OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<AirportEmployeeInfoEntity> employees = new ArrayList<>();

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    private List<FlightEntity> departingFlights = new ArrayList<>();

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<FlightEntity> arrivingFlights = new ArrayList<>();
}

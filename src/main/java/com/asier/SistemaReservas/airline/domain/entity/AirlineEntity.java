package com.asier.SistemaReservas.airline.domain.entity;

import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "airlines")
public class AirlineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    @Column(unique = true, length = 4)
    private String icaoCode;

    @Column(nullable = false, unique = true)
    private String name;

    private String country;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "airline")
    private List<FlightEntity> flights;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<AirlineEmployeeInfoEntity> employees = new ArrayList<>();
}

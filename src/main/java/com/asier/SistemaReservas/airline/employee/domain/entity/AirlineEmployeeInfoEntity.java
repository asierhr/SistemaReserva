package com.asier.SistemaReservas.airline.employee.domain.entity;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.employee.domain.enums.EmployeeType;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "airlineEmployees")
public class AirlineEmployeeInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "airportId")
    private AirportEntity airport;

    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private AirlineEntity airline;
}

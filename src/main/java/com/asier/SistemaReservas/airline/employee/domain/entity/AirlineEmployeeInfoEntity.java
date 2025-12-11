package com.asier.SistemaReservas.airline.employee.domain.entity;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
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
@Table(name = "airportEmployees")
public class AirlineEmployeeInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "airportId")
    private AirportEntity airport;

    @ManyToOne
    private AirlineEntity airline;
}

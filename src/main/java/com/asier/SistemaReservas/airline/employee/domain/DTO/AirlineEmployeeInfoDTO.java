package com.asier.SistemaReservas.airline.employee.domain.DTO;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirlineEmployeeInfoDTO {
    private Long id;
    private UserEntity user;
    private AirportEntity airport;
    private AirlineEntity airline;
}

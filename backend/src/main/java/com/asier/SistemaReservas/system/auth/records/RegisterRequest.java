package com.asier.SistemaReservas.system.auth.records;

import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.employee.domain.enums.EmployeeType;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.user.domain.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest{
    private String mail;
    private String name;
    private String password;
    private UserRole role;
    private Long hotelId;
    private Long airlineId;
    private Long airportId;
    private EmployeeType employeeType;
}

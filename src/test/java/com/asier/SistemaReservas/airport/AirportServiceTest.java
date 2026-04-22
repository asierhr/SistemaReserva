package com.asier.SistemaReservas.airport;

import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.aiport.repository.AirportRepository;
import com.asier.SistemaReservas.aiport.service.impl.AirportServiceImpl;
import com.asier.SistemaReservas.flight.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private AirportMapper airportMapper;
    @Mock
    private FlightServiceImpl flightService;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Test
    void createAirport(){

    }
}

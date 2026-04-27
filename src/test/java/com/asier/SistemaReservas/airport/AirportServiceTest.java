package com.asier.SistemaReservas.airport;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.aiport.repository.AirportRepository;
import com.asier.SistemaReservas.aiport.service.impl.AirportServiceImpl;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.flight.service.impl.FlightServiceImpl;
import com.asier.SistemaReservas.locationObjects.LocationObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private AirportMapper airportMapper;
    @Mock
    private FlightServiceImpl flightService;
    @Mock
    private AirlineEmployeeInfoService airportEmployeeInfoService;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Test
    void createAirport(){
        AirportDTO dto = new AirportDTO();
        dto.setAirportName("BCN");
        dto.setLocation(LocationObjects.location2());

        AirportEntity entity = AirportEntity.builder()
                .airportName("BCN")
                .location(LocationObjects.location2())
                .build();

        when(airportRepository.save(any())).thenReturn(entity);
        when(airportMapper.toDTO(entity)).thenReturn(dto);

        AirportDTO result = airportService.createAirport(dto);

        assertNotNull(result);
        assertEquals("BCN", result.getAirportName());
        verify(airportRepository).save(entity);
        verify(airportMapper).toDTO(entity);
    }

    @Test
    void getAirportExists() {
        AirportEntity entity = new AirportEntity();
        entity.setId(1L);
        when(airportRepository.findById(1L)).thenReturn(Optional.of(entity));
        AirportEntity result = airportService.getAirport(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAirportNotFound() {
        when(airportRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> airportService.getAirport(1L));
    }

    @Test
    void getAirportByName() {
        AirportEntity entity = new AirportEntity();
        AirportDTO dto = new AirportDTO();
        dto.setAirportName("BCN");
        when(airportRepository.findByAirportName("BCN")).thenReturn(Optional.of(entity));
        when(airportMapper.toDTO(entity)).thenReturn(dto);
        AirportDTO result = airportService.getAirportByName("BCN");
        assertNotNull(result);
        assertEquals("BCN", result.getAirportName());
    }
    @Test
    void getAirportByLocationCity() {
        AirportEntity entity = new AirportEntity();
        when(airportRepository.findByLocationCity("Barcelona")).thenReturn(entity);
        AirportEntity result = airportService.getAirportByLocationCity("Barcelona");
        assertNotNull(result);
    }


    @Test
    void getTodayFlights() {
        LocalDate today = LocalDate.now();

        FlightEntity earlyArrival = new FlightEntity();
        earlyArrival.setFlightDay(today.minusDays(1));
        earlyArrival.setArrivalTime(LocalTime.of(23, 0));

        FlightEntity validArrival = new FlightEntity();
        validArrival.setFlightDay(today);
        validArrival.setArrivalTime(LocalTime.of(10, 0));

        FlightEntity lateArrival = new FlightEntity();
        lateArrival.setFlightDay(today);
        lateArrival.setArrivalTime(LocalTime.of(23, 30));

        FlightEntity departure = new FlightEntity();
        departure.setFlightDay(today);
        departure.setDepartureTime(LocalTime.of(12, 0));

        AirportEntity airport = new AirportEntity();
        airport.setArrivingFlights(List.of(lateArrival, earlyArrival, validArrival));
        airport.setDepartingFlights(List.of(departure));

        AirlineEmployeeInfoEntity employeeInfo = new AirlineEmployeeInfoEntity();
        employeeInfo.setAirport(airport);

        when(airportEmployeeInfoService.getAirlineEmployeeInfo()).thenReturn(employeeInfo);

        FlightDTO arrivalDTO1 = new FlightDTO();
        arrivalDTO1.setArrivalTime(LocalTime.of(10, 0));

        FlightDTO arrivalDTO2 = new FlightDTO();
        arrivalDTO2.setArrivalTime(LocalTime.of(23, 0));

        FlightDTO arrivalDTO3 = new FlightDTO();
        arrivalDTO3.setArrivalTime(LocalTime.of(23, 30));

        FlightDTO departureDTO = new FlightDTO();
        departureDTO.setDepartureTime(LocalTime.of(12, 0));

        when(flightService.transformListEntity(any())).thenAnswer(invocation -> {
            List<FlightEntity> list = invocation.getArgument(0);
            if (list.size() == 3) return List.of(arrivalDTO1, arrivalDTO2, arrivalDTO3);
            if (list.size() == 1) return List.of(departureDTO);
            return List.of();
        });

        Map<String, List<FlightDTO>> result = airportService.getTodayFlights();

        List<FlightDTO> arrivals = result.get("arrivals");
        List<FlightDTO> departures = result.get("departures");

        assertEquals(3, arrivals.size());
        assertEquals(1, departures.size());

        assertEquals(LocalTime.of(10, 0), arrivals.get(0).getArrivalTime());
        assertEquals(LocalTime.of(23, 0), arrivals.get(1).getArrivalTime());
        assertEquals(LocalTime.of(23, 30), arrivals.get(2).getArrivalTime());

        assertEquals(LocalTime.of(12, 0), departures.get(0).getDepartureTime());
    }
}

package com.asier.SistemaReservas.seats;

import com.asier.SistemaReservas.flight.service.FlightHelper;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
import com.asier.SistemaReservas.seats.mapper.SeatMapper;
import com.asier.SistemaReservas.seats.objects.SeatObjects;
import com.asier.SistemaReservas.seats.repository.SeatRepository;
import com.asier.SistemaReservas.seats.service.impl.SeatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {
    @InjectMocks
    private SeatServiceImpl seatService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private SeatMapper seatMapper;
    @Mock
    private FlightHelper flightHelper;

    @Test
    void createSeats(){
        List<SeatDTO> seatDTOs = List.of(
                SeatObjects.seatDTO1(),
                SeatObjects.seatDTO2()
        );
        List<SeatEntity> entitiesToSave = List.of(
                SeatObjects.seat1(),
                SeatObjects.seat2()
        );
        List<SeatEntity> savedEntities = List.of(
                SeatObjects.seat1WithId(),
                SeatObjects.seat2WithId()
        );
        List<SeatDTO> expectedDTOs = List.of(
                SeatObjects.seatDTO1WithId(),
                SeatObjects.seatDTO2WithId()
        );
        when(seatMapper.toEntityList(seatDTOs)).thenReturn(entitiesToSave);
        when(seatRepository.saveAll(entitiesToSave)).thenReturn(savedEntities);
        when(seatMapper.toDTOList(savedEntities)).thenReturn(expectedDTOs);

        List<SeatDTO> result = seatService.createSeats(seatDTOs);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(expectedDTOs, result);

        verify(seatMapper).toEntityList(seatDTOs);
        verify(seatRepository).saveAll(entitiesToSave);
        verify(seatMapper).toDTOList(savedEntities);
    }

    @Test
    void getSeatsFromFlight() {
        Long flightId = 1L;
        List<SeatEntity> seats = List.of(
                SeatObjects.seat1WithId(),
                SeatObjects.seat2WithId(),
                SeatObjects.seat3WithId()
        );
        List<SeatDTO> seatDTOs = List.of(
                SeatObjects.seatDTO1WithId(),
                SeatObjects.seatDTO2WithId(),
                SeatObjects.seatDTO3WithId()
        );

        when(flightHelper.flightExists(flightId)).thenReturn(true);
        when(seatRepository.findAllByFlightId(flightId)).thenReturn(seats);
        when(seatMapper.toDTOList(seats)).thenReturn(seatDTOs);

        Map<SeatClass, List<SeatDTO>> result = seatService.getSeatsFromFlight(flightId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.containsKey(SeatClass.ECONOMY));
        assertTrue(result.containsKey(SeatClass.BUSINESS));
        assertTrue(result.containsKey(SeatClass.PREMIUM));
        assertEquals(1, result.get(SeatClass.ECONOMY).size());
        assertEquals(1, result.get(SeatClass.BUSINESS).size());
        assertEquals(1, result.get(SeatClass.PREMIUM).size());

        verify(flightHelper).flightExists(flightId);
        verify(seatRepository).findAllByFlightId(flightId);
        verify(seatMapper).toDTOList(seats);
    }

    @Test
    void getSeatsFromFlight_flightNotFound() {
        Long flightId = 999L;
        when(flightHelper.flightExists(flightId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> seatService.getSeatsFromFlight(flightId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Flight not found", exception.getReason());

        verify(flightHelper).flightExists(flightId);
        verify(seatRepository, never()).findAllByFlightId(any());
        verify(seatMapper, never()).toDTOList(any());
    }

    @Test
    void getSeatsFromFlight_noSeats() {
        Long flightId = 1L;
        List<SeatEntity> emptySeats = List.of();
        List<SeatDTO> emptyDTOs = List.of();

        when(flightHelper.flightExists(flightId)).thenReturn(true);
        when(seatRepository.findAllByFlightId(flightId)).thenReturn(emptySeats);
        when(seatMapper.toDTOList(emptySeats)).thenReturn(emptyDTOs);

        // Act
        Map<SeatClass, List<SeatDTO>> result = seatService.getSeatsFromFlight(flightId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(flightHelper).flightExists(flightId);
        verify(seatRepository).findAllByFlightId(flightId);
        verify(seatMapper).toDTOList(emptySeats);
    }
}

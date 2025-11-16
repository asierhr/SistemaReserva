package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.enums.SeatClass;
import com.asier.SistemaReservas.mapper.SeatMapper;
import com.asier.SistemaReservas.repositories.SeatRepository;
import com.asier.SistemaReservas.servicies.FlightService;
import com.asier.SistemaReservas.servicies.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final FlightService flightService;

    @Override
    public List<SeatDTO> createSeats(List<SeatDTO> seats) {
        List<SeatEntity> seatEntities = seatRepository.saveAll(seatMapper.toEntityList(seats));
        return seatMapper.toDTOList(seatEntities);
    }

    @Override
    public Map<SeatClass, List<SeatDTO>> getSeatsFromFlight(Long id) {
        if(!flightService.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        List<SeatEntity> seats = seatRepository.findAllByFlightId(id);
        List<SeatDTO> seatDTOs = seatMapper.toDTOList(seats);
        return seatDTOs.stream().collect(Collectors.groupingBy(SeatDTO::getSeatClass));
    }

    @Override
    public List<SeatEntity> getSeatFromIds(List<Long> seatIds) {
        return seatRepository.findAllById(seatIds);
    }

    @Override
    public void updateListSeatsAvailability(List<SeatEntity> seats) {
        seatRepository.saveAll(seats);
    }

    @Override
    public List<SeatEntity> getAvailableSeats(Long id) {
        return seatRepository.findAllByFlightIdAndAvailableTrue(id);
    }
}

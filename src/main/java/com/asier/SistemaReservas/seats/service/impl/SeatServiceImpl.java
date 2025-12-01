package com.asier.SistemaReservas.seats.service.impl;

import com.asier.SistemaReservas.flight.service.FlightHelper;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
import com.asier.SistemaReservas.seats.mapper.SeatMapper;
import com.asier.SistemaReservas.seats.repository.SeatRepository;
import com.asier.SistemaReservas.seats.service.SeatService;
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
    private final FlightHelper flightSeatHelper;

    @Override
    public List<SeatDTO> createSeats(List<SeatDTO> seats) {
        List<SeatEntity> seatEntities = seatRepository.saveAll(seatMapper.toEntityList(seats));
        return seatMapper.toDTOList(seatEntities);
    }

    @Override
    public Map<SeatClass, List<SeatDTO>> getSeatsFromFlight(Long id) {
        if(!flightSeatHelper.flightExists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
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

    @Override
    public SeatEntity getSeatFromId(Long id) {
        return seatRepository.findById(id).orElseThrow();
    }
}

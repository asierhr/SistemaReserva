package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.AirportDTO;
import com.asier.SistemaReservas.domain.entities.AirportEntity;
import com.asier.SistemaReservas.domain.entities.Location;
import com.asier.SistemaReservas.mapper.AirportMapper;
import com.asier.SistemaReservas.repositories.AirportRepository;
import com.asier.SistemaReservas.servicies.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportDTO createAirport(AirportDTO airportDTO) {
        AirportEntity airport = AirportEntity.builder()
                .location(airportDTO.getLocation())
                .airportName(airportDTO.getAirportName())
                .build();
        return airportMapper.toDTO(airportRepository.save(airport));
    }

    @Override
    public AirportEntity getAirport(Long id) {
        return airportRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public AirportDTO getAirportByName(String name) {
        return airportMapper.toDTO(airportRepository.findByAirportName(name).orElseThrow());
    }

    @Override
    public AirportEntity getAirportByLocationCity(String city) {
        return airportRepository.findByLocationCity(city);
    }
}

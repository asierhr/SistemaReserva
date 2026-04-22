package com.asier.SistemaReservas.airline.service.impl;

import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.repository.AirlineRepository;
import com.asier.SistemaReservas.airline.service.AirlineHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineHelperImpl implements AirlineHelper {
    private final AirlineRepository airlineRepository;

    @Override
    public AirlineEntity getAirline(Long id) {
        return airlineRepository.findById(id).orElseThrow();
    }
}

package com.asier.SistemaReservas.search.flightSearch.service.impl;

import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;
import com.asier.SistemaReservas.search.flightSearch.mapper.FlightSearchMapper;
import com.asier.SistemaReservas.search.flightSearch.repository.FlightSearchRepository;
import com.asier.SistemaReservas.search.flightSearch.service.FlightSearchService;
import com.asier.SistemaReservas.system.IpLocation.domain.Location;
import com.asier.SistemaReservas.system.IpLocation.service.IpGeolocationService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSearchServiceImpl implements FlightSearchService{
    private final FlightSearchRepository flightSearchRepository;
    private final UserService userService;
    private final FlightSearchMapper flightSearchMapper;
    private final IpGeolocationService ipGeolocationService;

    @Override
    @Transactional
    public void saveSearch(FlightSearchDTO flightSearch, String ipAddress) {
        UserEntity user = userService.getUserEntity();
        FlightSearchEntity flight = flightSearchMapper.toEntity(flightSearch);
        flight.setUser(user);

        flightSearchRepository.save(flight);

        setGeolocation(flight.getId(), ipAddress);
    }

    private void setGeolocation(Long searchId, String ipAddress){
        ipGeolocationService.getLocationByIp(ipAddress)
                .map(location -> {
                    flightSearchRepository.findById(searchId).ifPresent(flight -> {
                        flight.setLocation(location);
                        flightSearchRepository.save(flight);
                    });
                    return null;
                })
                .onErrorResume(error -> {
                    log.error("Error enriqueciendo con geolocalización búsqueda ID: {}", searchId, error);
                    return Mono.empty();
                })
                .then();
    }

    @Override
    public List<FlightSearchDTO> getSearchByUser() {
        UserEntity user = userService.getUserEntity();
        return flightSearchMapper.toDTOList(flightSearchRepository.findAllByUserId(user.getId()));
    }
}

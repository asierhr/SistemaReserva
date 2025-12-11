package com.asier.SistemaReservas.search.hotelSearch.service.impl;

import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.search.hotelSearch.domain.entity.HotelSearchEntity;
import com.asier.SistemaReservas.search.hotelSearch.mapper.HotelSearchMapper;
import com.asier.SistemaReservas.search.hotelSearch.repository.HotelSearchRepository;
import com.asier.SistemaReservas.search.hotelSearch.service.HotelSearchService;
import com.asier.SistemaReservas.system.IpLocation.service.IpGeolocationService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelSearchServiceImpl implements HotelSearchService {
    private final HotelSearchMapper hotelSearchMapper;
    private final HotelSearchRepository hotelSearchRepository;
    private final UserService userService;
    private final IpGeolocationService ipGeolocationService;

    @Override
    @Async
    public void saveHotelSearch(HotelSearchDTO hotelSearchDTO, String ipAddress) {
        UserEntity user = userService.getUserEntity();
        HotelSearchEntity hotelSearch = hotelSearchMapper.toEntity(hotelSearchDTO);
        hotelSearch.setUser(user);
        hotelSearchRepository.save(hotelSearch);

        setGeolocation(hotelSearch.getId(),ipAddress);
    }

    private void setGeolocation(Long searchId, String ipAddress){
        ipGeolocationService.getLocationByIp(ipAddress)
                .map(location -> {
                    hotelSearchRepository.findById(searchId).ifPresent(hotel -> {
                        hotel.setLocation(location);
                        hotelSearchRepository.save(hotel);
                    });
                    return null;
                })
                .onErrorResume(error -> {
                    log.error("Error enriqueciendo con geolocalización búsqueda ID: {}", searchId, error);
                    return Mono.empty();
                })
                .then()
                .toFuture();
    }

    @Override
    public List<HotelSearchDTO> getHotelSearchByUser() {
        UserEntity user = userService.getUserEntity();
        return hotelSearchMapper.toDTOList(hotelSearchRepository.findAllByUserId(user.getId()));
    }
}

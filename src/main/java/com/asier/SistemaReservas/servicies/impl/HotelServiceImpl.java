package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.HotelDTO;
import com.asier.SistemaReservas.domain.dto.HotelSummaryDTO;
import com.asier.SistemaReservas.domain.entities.HotelEntity;
import com.asier.SistemaReservas.mapper.HotelMapper;
import com.asier.SistemaReservas.repositories.HotelRepository;
import com.asier.SistemaReservas.servicies.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;


    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        HotelEntity hotel = hotelMapper.toEntity(hotelDTO);
        hotel.getRooms().forEach(roomEntity -> {
            roomEntity.setHotel(hotel);
            roomEntity.setAvailable(true);
        });
        HotelEntity savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDTO(savedHotel);
    }

    @Override
    public HotelSummaryDTO getHotel(Long id) {
        HotelEntity hotel = getHotelEntity(id);
        return hotelMapper.toSummaryDTO(hotel);
    }

    @Override
    public HotelEntity getHotelEntity(Long id) {
        return hotelRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    }

    @Override
    public boolean existsHotel(Long id) {
        return hotelRepository.existsById(id);
    }
}

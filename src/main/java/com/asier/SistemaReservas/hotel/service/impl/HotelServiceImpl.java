package com.asier.SistemaReservas.hotel.service.impl;

import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.hotel.history.service.HotelHistoryService;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.hotel.repository.HotelRepository;
import com.asier.SistemaReservas.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelHistoryService hotelHistoryService;


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
        if(!hotelHistoryService.existsHotelHistory()) hotelHistoryService.createHotelHistory();
        hotelHistoryService.updateHotelHistory(hotel);
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

    @Override
    public void updateRating(HotelEntity hotel) {
        List<CommentEntity> comments = hotel.getComments();
        Double rating = 0.0;
        for(CommentEntity comment: comments) rating += comment.getRating();
        hotel.setRating(rating/comments.size());
        hotelRepository.save(hotel);
    }
}

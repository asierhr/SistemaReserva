package com.asier.SistemaReservas.hotel.history.service.impl;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.history.domain.DTO.HotelHistoryDTO;
import com.asier.SistemaReservas.hotel.history.domain.entity.HotelHistoryEntity;
import com.asier.SistemaReservas.hotel.history.mapper.HotelHistoryMapper;
import com.asier.SistemaReservas.hotel.history.repository.HotelHistoryRepository;
import com.asier.SistemaReservas.hotel.history.service.HotelHistoryService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelHistoryServiceImpl implements HotelHistoryService {
    private final HotelHistoryRepository hotelHistoryRepository;
    private final UserService userService;
    private final HotelHistoryMapper hotelHistoryMapper;

    @Override
    public void createHotelHistory() {
        HotelHistoryEntity hotelHistory = new HotelHistoryEntity();
        hotelHistory.setUser(userService.getUserEntity());
        hotelHistoryRepository.save(hotelHistory);
    }

    @Override
    public void updateHotelHistory(HotelEntity hotel) {
        UserEntity user = userService.getUserEntity();
        HotelHistoryEntity hotelHistory = hotelHistoryRepository.findByUserId(user.getId());
        hotelHistory.getHotels().remove(hotel);
        hotelHistory.getHotels().add(0,hotel);
        if(hotelHistory.getHotels().size() > 10) hotelHistory.getHotels().subList(0,10);
        hotelHistoryRepository.save(hotelHistory);
    }

    @Override
    public HotelHistoryDTO getHotelHistory() {
        UserEntity user = userService.getUserEntity();
        HotelHistoryEntity hotelHistory = hotelHistoryRepository.findByUserId(user.getId());
        return hotelHistoryMapper.toDTO(hotelHistory);
    }

    @Override
    public boolean existsHotelHistory() {
        UserEntity user = userService.getUserEntity();
        return hotelHistoryRepository.existsByUserId(user.getId());
    }
}

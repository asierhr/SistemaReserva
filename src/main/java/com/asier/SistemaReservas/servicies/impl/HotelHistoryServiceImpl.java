package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.HotelHistoryDTO;
import com.asier.SistemaReservas.domain.entities.HotelEntity;
import com.asier.SistemaReservas.domain.entities.HotelHistoryEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.mapper.HotelHistoryMapper;
import com.asier.SistemaReservas.repositories.HotelHistoryRepository;
import com.asier.SistemaReservas.servicies.HotelHistoryService;
import com.asier.SistemaReservas.servicies.UserService;
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
        hotelHistory.getHotels().addFirst(hotel);
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

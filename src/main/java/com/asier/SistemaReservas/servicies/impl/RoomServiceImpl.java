package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.entities.RoomEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;
import com.asier.SistemaReservas.domain.records.HotelSearch;
import com.asier.SistemaReservas.mapper.RoomMapper;
import com.asier.SistemaReservas.repositories.RoomRepository;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;

    @Override
    public Map<RoomType, List<RoomDTO>> getRooms(Long id) {
        if(!hotelService.existsHotel(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<RoomEntity> roomEntities = roomRepository.findAllByHotelId(id);
        List<RoomDTO> roomDTOS = roomMapper.toDTOList(roomEntities);
        return roomDTOS.stream().collect(Collectors.groupingBy(RoomDTO::getType));
    }

    @Override
    public List<RoomEntity> getRoomsFromIds(List<Long> id) {
        return roomRepository.findAllById(id);
    }


    @Override
    public Set<List<RoomDTO>> getRoomsBySearch(HotelSearch hotelSearch) {
        int totalGuests = hotelSearch.guests();
        String city = hotelSearch.city();

        // Combinaciones de tipos de habitación
        List<List<RoomType>> combinations = findRoomCombinations(totalGuests);

        // Todos los tipos permitidos en las combinaciones
        List<RoomType> allowedTypes = combinations.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();

        Double averagePrice = roomRepository.findAveragePriceByCityAndRoomType(city, allowedTypes);
        Double stdPrice = roomRepository.findPriceStdDevByCity(city, allowedTypes);

        Double minPrice = averagePrice - stdPrice;
        Double maxPrice = averagePrice + stdPrice;

        Set<List<RoomDTO>> matchingRoomCombinations = new HashSet<>();


        List<RoomEntity> candidateRooms = roomRepository.findRoomsNearAverage(city, allowedTypes, minPrice, maxPrice);

        Map<Long, List<RoomEntity>> roomsByHotel = candidateRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getHotel().getId()));

        for (Map.Entry<Long, List<RoomEntity>> entry : roomsByHotel.entrySet()) {
            Long hotelId = entry.getKey();
            List<RoomEntity> roomsInHotel = entry.getValue();

            for (List<RoomType> combo : combinations) {
                List<RoomEntity> matchingRooms = roomsInHotel.stream()
                        .filter(r -> combo.contains(r.getType()))
                        .toList();

                int totalCapacity = matchingRooms.stream()
                        .mapToInt(r -> r.getType().getCapacity())
                        .sum();

                if (totalCapacity >= totalGuests) {
                    List<RoomEntity> minimalRooms = new ArrayList<>();
                    int count = 0;
                    for (RoomEntity r : matchingRooms) {
                        minimalRooms.add(r);
                        count += r.getType().getCapacity();
                        if (count >= totalGuests) break;
                    }
                    matchingRoomCombinations.add(roomMapper.toDTOList(minimalRooms));
                }
            }
        }

        return matchingRoomCombinations;
    }

    private List<List<RoomType>> findRoomCombinations(int totalGuests) {
        List<RoomType> allTypes = Arrays.asList(RoomType.values());
        List<List<RoomType>> validCombinations = new ArrayList<>();

        for (RoomType first : allTypes) {
            if (first.getCapacity() >= totalGuests) {
                validCombinations.add(List.of(first));
            }
            for (RoomType second : allTypes) {
                if (first.getCapacity() + second.getCapacity() >= totalGuests) {
                    validCombinations.add(List.of(first, second));
                }
            }
        }

        return validCombinations;
    }
}

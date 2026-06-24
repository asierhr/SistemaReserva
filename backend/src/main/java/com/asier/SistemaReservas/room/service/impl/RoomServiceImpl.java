package com.asier.SistemaReservas.room.service.impl;

import com.asier.SistemaReservas.hotel.hotelDashboard.service.HotelDailyMetricsService;
import com.asier.SistemaReservas.room.domain.DTO.RoomCombination;
import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.enums.RoomType;
import com.asier.SistemaReservas.room.mapper.RoomMapper;
import com.asier.SistemaReservas.room.repository.RoomRepository;
import com.asier.SistemaReservas.room.service.RoomService;
import com.asier.SistemaReservas.search.hotelSearch.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;
    private final HotelSearchService hotelSearchService;
    private final HotelDailyMetricsService hotelDailyMetricsService;
    private final Map<Integer, List<List<RoomType>>> localCache =
            new ConcurrentHashMap<>();
    private RoomService self;

    @Autowired
    @Lazy
    public void setSelf(RoomService self) {
        this.self = self;
    }

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
    public Set<RoomCombination> getRoomsBySearch(HotelSearchDTO hotelSearch, String ipAddress) {
        hotelSearchService.saveHotelSearch(hotelSearch, ipAddress);

        int totalRooms = hotelSearch.getRooms();
        String city = hotelSearch.getCity();

        List<List<RoomType>> combinations = findRoomCombinations(totalRooms);

        List<RoomType> allowedTypes = combinations.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();

        List<RoomEntity> candidateRooms = roomRepository.findAvailableRooms(city,allowedTypes,hotelSearch.getCheckIn(),hotelSearch.getCheckOut());

        Map<Long,List<RoomEntity>> roomsByHotel = candidateRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getHotel().getId()));

        List<Long> hotelIds = new ArrayList<>(roomsByHotel.keySet());
        Map<Long,Integer> searchesByHotel = hotelDailyMetricsService.getSearchCountsByHotels(hotelIds);

        hotelIds.sort((a, b) -> searchesByHotel.getOrDefault(b, 0).compareTo(searchesByHotel.getOrDefault(a, 0)));

        Set<RoomCombination> matchingRoomCombinations = new LinkedHashSet<>();

        for(Long hotelId: hotelIds){
            List<RoomEntity> roomsInHotel = roomsByHotel.get(hotelId);
            Map<RoomType, List<RoomEntity>> roomsByType =
                    roomsInHotel.stream().collect(Collectors.groupingBy(RoomEntity::getType));
            for (List<RoomType> combo : combinations) {
                List<RoomEntity> selected = selectRoomsForCombination(roomsByType, combo, totalRooms);
                if (selected != null) {
                    List<RoomDTO> dto = roomMapper.toDTOList(selected);
                    matchingRoomCombinations.add(new RoomCombination(hotelId,dto,dto.stream()
                            .map(RoomDTO::getCostPerNight)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)));
                }
            }
        }
        return matchingRoomCombinations;
    }

    private List<RoomEntity> selectRoomsForCombination(Map<RoomType, List<RoomEntity>> roomsByType,
                                                       List<RoomType> combo,
                                                       int totalRooms
    ) {
        List<RoomEntity> selected = new ArrayList<>();

        for (RoomType type : combo) {
            List<RoomEntity> list = roomsByType.get(type);
            if (list == null || list.isEmpty()) return null;
            selected.add(list.get(0));
        }

        int capacity = selected.stream()
                .mapToInt(r -> r.getType().getCapacity())
                .sum();

        return capacity >= totalRooms ? selected : null;
    }

    private List<List<RoomType>> findRoomCombinations(int totalRooms) {
        return localCache.computeIfAbsent(totalRooms,
                self::findRoomCombinationsFromRedis);
    }

    @Override
    @Cacheable(value = "roomCombinations", key = "#totalRooms")
    public List<List<RoomType>> findRoomCombinationsFromRedis(int totalRooms) {
        List<RoomType> types = Arrays.stream(RoomType.values())
                .sorted(Comparator.comparingInt(RoomType::getCapacity).reversed())
                .toList();

        List<List<RoomType>> result = new ArrayList<>();
        findCombinationsRecursive(totalRooms, 0, types, new ArrayList<>(), result, 0);

        return result;
    }

    private void findCombinationsRecursive(
            int remaining,
            int roomsUsed,
            List<RoomType> types,
            List<RoomType> current,
            List<List<RoomType>> result,
            int startIndex  // NUEVO
    ){
        if(remaining <= 0){
            result.add(new ArrayList<>(current));
            return;
        }

        if(roomsUsed == 3) return;

        for (int i = startIndex; i < types.size(); i++) {
            RoomType type = types.get(i);

            if (type.getCapacity() > remaining * 1.5) continue;

            current.add(type);
            findCombinationsRecursive(remaining - type.getCapacity(),
                    roomsUsed + 1, types, current, result, i);
            current.remove(current.size() - 1);
        }
    }

    @Override
    public List<RoomEntity> findSimilarAvailableRooms(Long id, RoomType type, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByTypes(id,type,checkIn,checkOut);
    }
}

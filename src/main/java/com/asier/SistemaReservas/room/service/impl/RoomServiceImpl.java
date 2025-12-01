package com.asier.SistemaReservas.room.service.impl;

import com.asier.SistemaReservas.hotel.domain.records.HotelSearch;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.enums.RoomType;
import com.asier.SistemaReservas.room.mapper.RoomMapper;
import com.asier.SistemaReservas.room.repository.RoomRepository;
import com.asier.SistemaReservas.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

        List<List<RoomType>> combinations = findRoomCombinations(totalGuests);

        List<RoomType> allowedTypes = combinations.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();

        List<RoomEntity> candidateRooms = roomRepository.findAvailableRooms(city,allowedTypes,hotelSearch.checkIn(),hotelSearch.checkOut());

        Map<Long,List<RoomEntity>> roomsByHotel = candidateRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getHotel().getId()));

        Set<List<RoomDTO>> matchingRoomCombinations = new HashSet<>();

        for(Map.Entry<Long, List<RoomEntity>> entry: roomsByHotel.entrySet()){
            List<RoomEntity> roomsInHotel = entry.getValue();

            for (List<RoomType> combo : combinations) {
                List<RoomEntity> selectedRooms = selectRoomsForCombination(
                        roomsInHotel,
                        combo,
                        totalGuests
                );
                if (selectedRooms != null) {
                    matchingRoomCombinations.add(roomMapper.toDTOList(selectedRooms));
                }
            }
        }
        return matchingRoomCombinations;
    }

    private List<RoomEntity> selectRoomsForCombination(List<RoomEntity> availableRooms, List<RoomType> combo, int totalGuests){
        List<RoomEntity> selected = new ArrayList<>();
        List<RoomEntity> remaining = new ArrayList<>(availableRooms);

        remaining.sort(Comparator.comparing(RoomEntity::getCostPerNight));

        for(RoomType neededType : combo){
            RoomEntity room = remaining.stream()
                    .filter(r -> r.getType() == neededType)
                    .findFirst()
                    .orElse(null);

            if(room == null){
                return null;
            }

            selected.add(room);
            remaining.remove(room);
        }

        int totalCapacity = selected.stream()
                .mapToInt(r->r.getType().getCapacity())
                .sum();

        return totalCapacity >= totalGuests ? selected : null;
    }

    private List<List<RoomType>> findRoomCombinations(int totalGuests) {
        List<RoomType> allTypes = Arrays.asList(RoomType.values());
        List<List<RoomType>> validCombinations = new ArrayList<>();

        List<RoomType> sortedTypes = allTypes.stream()
                .sorted(Comparator.comparingInt(RoomType::getCapacity).reversed())
                .toList();

        findCombinationsRecursive(sortedTypes, totalGuests, new ArrayList<>(),
                validCombinations, 0,3);

        return validCombinations.stream()
                .filter(combo -> {
                    int capacity = combo.stream()
                            .mapToInt(RoomType::getCapacity)
                            .sum();
                    int waste = capacity - totalGuests;
                    return waste <= totalGuests * 0.5;
                })
                .collect(Collectors.toList());
    }

    private void findCombinationsRecursive(
            List<RoomType> types,
            int remainingGuests,
            List<RoomType> current,
            List<List<RoomType>> results,
            int startIndex,
            int maxRooms
    ){
        if(remainingGuests <= 0){
            results.add(new ArrayList<>(current));
            return;
        }

        if(current.size() >= maxRooms){
            return;
        }

        for(int i = startIndex; i < types.size(); i++){
            RoomType type = types.get(i);
            current.add(type);

            findCombinationsRecursive(types,remainingGuests-type.getCapacity(),current,results,i,maxRooms);

            current.remove(current.size() - 1);
        }

    }

    @Override
    public List<RoomEntity> findSimilarAvailableRooms(Long id, RoomType type, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByTypes(id,type,checkIn,checkOut);
    }
}

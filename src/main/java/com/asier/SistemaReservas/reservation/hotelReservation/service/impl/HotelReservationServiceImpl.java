package com.asier.SistemaReservas.reservation.hotelReservation.service.impl;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.HotelReservationCreatedEvent;
import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.service.LoyaltyBenefitsService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedEvent;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.records.ReservationHotelRequest;
import com.asier.SistemaReservas.reservation.hotelReservation.repository.HotelReservationRepository;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.entity.RoomReservationEntity;
import com.asier.SistemaReservas.room.domain.enums.RoomType;
import com.asier.SistemaReservas.system.Locks.DistributedLockService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.hotelReservation.mapper.HotelReservationMapper;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.room.service.RoomService;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelReservationServiceImpl implements HotelReservationService {
    private final HotelReservationRepository hotelReservationRepository;
    private final HotelReservationMapper hotelReservationMapper;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService userService;
    private final DistributedLockService distributedLockService;
    private final ApplicationEventPublisher eventPublisher;
    private final LoyaltyBenefitsService loyaltyBenefitsService;
    private final LoyaltyService loyaltyService;

    private static final int MAX_RETRIES = 3;

    private BigDecimal validateRooms(Long id, List<RoomEntity> rooms){
        if(rooms.isEmpty())  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No rooms provided");
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(RoomEntity room: rooms){
            if(!id.equals(room.getHotel().getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Room " + room.getId() + " does not belong to hotel " + id);
            totalPrice = totalPrice.add(room.getCostPerNight());
        }
        return totalPrice;
    }

    @Override
    public HotelReservationDTO createReservation(Long id, ReservationHotelRequest request) {
        if(!hotelService.existsHotel(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<RoomEntity> rooms = roomService.getRoomsFromIds(request.roomIds());

        UserEntity user = userService.getUserEntity();

        for(int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            List<String> roomLockKeys = rooms.stream()
                    .map(room -> "room:" + room.getId())
                    .toList();

            try {
                HotelReservationDTO hotelReservation = distributedLockService.executeWithMultiLock(roomLockKeys, () -> {
                    return processReservationInTransaction(id, request, user);
                });

                HotelReservationEntity savedEntity = hotelReservationRepository.findById(hotelReservation.getId()).orElseThrow();

                eventPublisher.publishEvent(new ReservationCreatedEvent(savedEntity));
                eventPublisher.publishEvent(new HotelReservationCreatedEvent(savedEntity.getHotel().getId(), savedEntity.getId()));

            } catch (ResponseStatusException e) {
                if (e.getStatusCode() == HttpStatus.CONFLICT && attempt < MAX_RETRIES - 1) {
                    rooms = findSimilarAvailableRooms(id, rooms, request);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not complete reservation");
    }

    @Transactional
    private HotelReservationDTO processReservationInTransaction(Long hotelId, ReservationHotelRequest request, UserEntity user){
        List<RoomEntity> newRooms = roomService.getRoomsFromIds(request.roomIds());

        for(RoomEntity room: newRooms){
            if(!room.isAvailable()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Room " + room.getId() + " was just taken");
            }
        }

        BigDecimal price = validateRooms(hotelId, newRooms);
        LoyaltyTierEntity tier = loyaltyService.getLoyaltyByUser(userService.getUserEntity().getId()).getLoyaltyTier();

        HotelReservationEntity hotel = HotelReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .totalPrice(validateRooms(hotelId, newRooms))
                .totalPriceAfterDiscount(loyaltyBenefitsService.applyBenefits(user,price).finalPrice())
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .user(user)
                .hotel(hotelService.getHotelEntity(hotelId))
                .totalGuests(request.totalGuests())
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .cancellationDeadline(loyaltyBenefitsService.getCancellationDeadline(tier,LocalDateTime.of(request.checkIn(), LocalTime.now())))
                .build();

        List<RoomReservationEntity> roomReservations = new ArrayList<>();

        for (RoomEntity room : newRooms) {
            RoomReservationEntity rr = RoomReservationEntity.builder()
                    .room(room)
                    .reservation(hotel)
                    .build();

            roomReservations.add(rr);

            room.setAvailable(false);
        }

        hotel.setRooms(roomReservations);

        HotelReservationEntity savedReservation = hotelReservationRepository.save(hotel);
        return hotelReservationMapper.toDTO(savedReservation);
    }

    private List<RoomEntity> findSimilarAvailableRooms(Long id, List<RoomEntity> originalRooms, ReservationHotelRequest request){
        Map<RoomType, Long> roomTypeCount = originalRooms.stream()
                .collect(Collectors.groupingBy(
                        RoomEntity::getType,
                        Collectors.counting()
                ));
        return findExactCombination(id,roomTypeCount,request);
    }

    private List<RoomEntity> findExactCombination(Long id, Map<RoomType, Long> roomTypeCount, ReservationHotelRequest request){
        List<RoomEntity> selectedRooms = new ArrayList<>();
        for(Map.Entry<RoomType,Long> entry: roomTypeCount.entrySet()){
            RoomType type = entry.getKey();
            int needed = entry.getValue().intValue();

            List<RoomEntity> availableRooms = roomService.findSimilarAvailableRooms(id,type,request.checkIn(), request.checkOut());

            if(availableRooms.size() < needed){
                return Collections.emptyList();
            }

            selectedRooms.addAll(
                    availableRooms.stream()
                            .limit(needed)
                            .toList()
            );
        }
        return selectedRooms;
    }

    @Override
    public List<HotelReservationDTO> getUserReservations() {
        UserEntity user = userService.getUserEntity();
        return hotelReservationMapper.toDTOList(hotelReservationRepository.findAllByUserId(user.getId()));
    }

    @Override
    public HotelReservationEntity getReservationById(Long id) {
        return hotelReservationRepository.findById(id).orElseThrow();
    }
}

package com.asier.SistemaReservas.comment.service.impl;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.comment.mapper.CommentMapper;
import com.asier.SistemaReservas.comment.repository.CommentRepository;
import com.asier.SistemaReservas.comment.service.CommentService;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.CommentCreatedEvent;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final HotelService hotelService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public CommentDTO createComment(Long hotelId, CommentDTO commentDTO) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        CommentEntity comment = commentMapper.toEntity(commentDTO);
        HotelEntity hotel = hotelService.getHotelEntity(hotelId);
        comment.setUser(userService.getUserEntity());
        comment.setHotel(hotel);
        CommentEntity savedComment = commentRepository.save(comment);
        eventPublisher.publishEvent(new CommentCreatedEvent(hotelId));
        return commentMapper.toDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getComments(Long hotelId) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<CommentEntity> comments = commentRepository.findAllByHotelId(hotelId);
        return commentMapper.toDTOList(comments);
    }
}

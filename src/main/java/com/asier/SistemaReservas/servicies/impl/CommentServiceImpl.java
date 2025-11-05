package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.CommentDTO;
import com.asier.SistemaReservas.domain.entities.CommentEntity;
import com.asier.SistemaReservas.domain.entities.HotelEntity;
import com.asier.SistemaReservas.mapper.CommentMapper;
import com.asier.SistemaReservas.repositories.CommentRepository;
import com.asier.SistemaReservas.servicies.CommentService;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public CommentDTO createComment(Long hotelId, CommentDTO commentDTO) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        CommentEntity comment = commentMapper.toEntity(commentDTO);
        HotelEntity hotel = hotelService.getHotelEntity(hotelId);
        comment.setUser(userService.getUserEntity());
        comment.setHotel(hotel);
        CommentEntity savedComment = commentRepository.save(comment);
        hotelService.updateRating(hotel);
        return commentMapper.toDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getComments(Long hotelId) {
        if(!hotelService.existsHotel(hotelId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<CommentEntity> comments = commentRepository.findAllByHotelId(hotelId);
        return commentMapper.toDTOList(comments);
    }
}

package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long hotelId, CommentDTO comment);
    List<CommentDTO> getComments(Long hotelId);
}

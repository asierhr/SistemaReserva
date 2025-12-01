package com.asier.SistemaReservas.comment.service;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long hotelId, CommentDTO comment);
    List<CommentDTO> getComments(Long hotelId);
}

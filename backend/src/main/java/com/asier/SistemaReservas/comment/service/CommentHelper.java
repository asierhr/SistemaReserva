package com.asier.SistemaReservas.comment.service;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;

import java.util.List;

public interface CommentHelper {
    List<CommentDTO> transformToDTOList(List<CommentEntity> comments);
}

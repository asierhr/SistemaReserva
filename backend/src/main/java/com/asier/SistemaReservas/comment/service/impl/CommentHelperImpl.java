package com.asier.SistemaReservas.comment.service.impl;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.comment.mapper.CommentMapper;
import com.asier.SistemaReservas.comment.service.CommentHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentHelperImpl implements CommentHelper {
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> transformToDTOList(List<CommentEntity> comments){
        return commentMapper.toDTOList(comments);
    }
}

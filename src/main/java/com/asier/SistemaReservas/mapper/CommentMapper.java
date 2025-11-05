package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.CommentDTO;
import com.asier.SistemaReservas.domain.entities.CommentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface CommentMapper {
    CommentDTO toDTO(CommentEntity comment);
    CommentEntity toEntity(CommentDTO comment);
    List<CommentDTO> toDTOList(List<CommentEntity> comments);
}

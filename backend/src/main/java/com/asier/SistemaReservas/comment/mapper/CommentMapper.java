package com.asier.SistemaReservas.comment.mapper;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface CommentMapper {
    CommentDTO toDTO(CommentEntity comment);
    CommentEntity toEntity(CommentDTO comment);
    List<CommentDTO> toDTOList(List<CommentEntity> comments);
}

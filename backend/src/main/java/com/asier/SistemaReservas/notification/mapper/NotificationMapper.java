package com.asier.SistemaReservas.notification.mapper;

import com.asier.SistemaReservas.notification.domain.DTO.NotificationDTO;
import com.asier.SistemaReservas.notification.domain.entity.NotificationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDTO toDTO(NotificationEntity notification);
    List<NotificationDTO> toDTOList(List<NotificationEntity> notifications);
}

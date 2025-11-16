package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.PaymentDTO;
import com.asier.SistemaReservas.domain.entities.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(PaymentEntity paymentEntity);
    PaymentEntity toEntity(PaymentDTO payment);
}

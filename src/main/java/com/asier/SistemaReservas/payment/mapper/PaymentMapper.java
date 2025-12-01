package com.asier.SistemaReservas.payment.mapper;

import com.asier.SistemaReservas.payment.domain.DTO.PaymentDTO;
import com.asier.SistemaReservas.payment.domain.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(PaymentEntity paymentEntity);
    PaymentEntity toEntity(PaymentDTO payment);
}

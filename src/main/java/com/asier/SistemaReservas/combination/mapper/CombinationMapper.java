package com.asier.SistemaReservas.combination.mapper;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.combination.domain.entity.CombinationEntity;

public interface CombinationMapper {
    CombinationEntity toEntity(CombinationDTO combination);
    CombinationDTO toDTO(CombinationEntity combination);
}

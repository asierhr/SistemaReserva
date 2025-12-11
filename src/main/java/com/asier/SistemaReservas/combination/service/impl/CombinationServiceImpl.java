package com.asier.SistemaReservas.combination.service.impl;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.combination.domain.entity.CombinationEntity;
import com.asier.SistemaReservas.combination.mapper.CombinationMapper;
import com.asier.SistemaReservas.combination.repository.CombinationRepository;
import com.asier.SistemaReservas.combination.service.CombinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CombinationServiceImpl implements CombinationService {
    private final CombinationRepository combinationRepository;
    private final CombinationMapper combinationMapper;

    @Override
    public CombinationDTO createCombination(CombinationDTO combinationDTO) {
        CombinationEntity combination = combinationMapper.toEntity(combinationDTO);
        CombinationEntity savedCombination = combinationRepository.save(combination);
        return combinationMapper.toDTO(savedCombination);
    }



}

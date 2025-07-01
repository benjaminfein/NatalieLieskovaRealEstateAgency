package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.exception.CommercialRealEstateNotFoundException;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.CommercialRealEstateMapper;
import com.example.natalielieskovarealestateagency.model.CommercialRealEstate;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.CommercialRealEstateRepository;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.CommercialRealEstateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommercialRealEstateServiceImpl implements CommercialRealEstateService {
    private final CommercialRealEstateRepository commercialRealEstateRepository;
    private final ResidentialComplexRepository residentialComplexRepository;

    @Override
    public CommercialRealEstateDTO createCommercialRealEstate(CommercialRealEstateDTO dto) {
        ResidentialComplex complex = null;
        if (dto.getResidentialComplexName() != null && !dto.getResidentialComplexName().isBlank()) {
            complex = residentialComplexRepository.findByName(dto.getResidentialComplexName())
                    .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));
        }

        CommercialRealEstate entity = CommercialRealEstateMapper.toEntity(dto, complex);
        CommercialRealEstate saved = commercialRealEstateRepository.save(entity);
        return CommercialRealEstateMapper.toDTO(saved);
    }

    @Override
    public CommercialRealEstateDTO findCommercialRealEstateById(Long id) {
        CommercialRealEstate entity = commercialRealEstateRepository.findById(id)
                .orElseThrow(() -> new CommercialRealEstateNotFoundException("Not found with id: " + id));
        return CommercialRealEstateMapper.toDTO(entity);
    }

    @Override
    public List<CommercialRealEstateDTO> findAllCommercialRealEstate() {
        return commercialRealEstateRepository.findAll().stream()
                .map(CommercialRealEstateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommercialRealEstateDTO updateCommercialRealEstate(Long id, CommercialRealEstateDTO dto) {
        CommercialRealEstate existing = commercialRealEstateRepository.findById(id)
                .orElseThrow(() -> new CommercialRealEstateNotFoundException("Не найден объект с id: " + id));

        ResidentialComplex complex = null;
        if (dto.getResidentialComplexName() != null && !dto.getResidentialComplexName().isBlank()) {
            complex = residentialComplexRepository.findByName(dto.getResidentialComplexName())
                    .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));
        }

        CommercialRealEstate updated = CommercialRealEstateMapper.toEntity(dto, complex);
        updated.setId(id); // сохранить ID
        return CommercialRealEstateMapper.toDTO(commercialRealEstateRepository.save(updated));
    }

    @Override
    public void deleteCommercialRealEstate(Long id) {
        commercialRealEstateRepository.deleteById(id);
    }

    @Override
    public List<String> getAllDistricts() {
        return commercialRealEstateRepository.findAllDistinctMicroDistricts();
    }

    @Override
    public List<Integer> getAllRoomCounts() {
        return commercialRealEstateRepository.findAllDistinctRoomCounts();
    }
}
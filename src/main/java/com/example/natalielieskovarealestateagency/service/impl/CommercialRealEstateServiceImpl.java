package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.exception.CommercialRealEstateNotFoundException;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.CommercialRealEstateMapper;
import com.example.natalielieskovarealestateagency.model.CommercialRealEstate;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.CommercialRealEstateRepository;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.CommercialRealEstateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommercialRealEstateServiceImpl implements CommercialRealEstateService {
    private final CommercialRealEstateRepository commercialRealEstateRepository;
    private final ResidentialComplexRepository residentialComplexRepository;

    @Override
    public CommercialRealEstateDTO createCommercialRealEstate(CommercialRealEstateDTO dto) {
        ResidentialComplex complex = null;
        if (dto.getNameOfResidentialComplex() != null && !dto.getNameOfResidentialComplex().isBlank()) {
            complex = residentialComplexRepository.findByName(dto.getNameOfResidentialComplex())
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
    public PagedResponse<CommercialRealEstateDTO> findAllCommercialRealEstate(Pageable pageable) {
        Page<CommercialRealEstate> commercialPage = commercialRealEstateRepository.findAll(pageable);

        List<CommercialRealEstateDTO> dtoList = commercialPage.getContent()
                .stream()
                .map(CommercialRealEstateMapper::toDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                commercialPage.getNumber(),
                commercialPage.getSize(),
                commercialPage.getTotalElements()
        );
    }

    @Override
    public CommercialRealEstateDTO updateCommercialRealEstate(Long id, CommercialRealEstateDTO commercialRealEstateDTO) {
        CommercialRealEstate existing = commercialRealEstateRepository.findById(id)
                .orElseThrow(() -> new CommercialRealEstateNotFoundException("Не найден объект с id: " + id));

        ResidentialComplex complex = null;
        if (commercialRealEstateDTO.getNameOfResidentialComplex() != null && !commercialRealEstateDTO
                .getNameOfResidentialComplex()
                .isBlank()) {
            complex = residentialComplexRepository.findByName(commercialRealEstateDTO.getNameOfResidentialComplex())
                    .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));
        }

        CommercialRealEstate updated = CommercialRealEstateMapper.toEntity(commercialRealEstateDTO, complex);
        updated.setId(id);
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

    @Override
    public CommercialRealEstateDTO getLastCreatedCommercialByUserId(Long adminCreatorId) {
        return commercialRealEstateRepository
                .findTopByAdminCreatorIdOrderByIdDesc(adminCreatorId)
                .map(CommercialRealEstateMapper::toDTO)
                .orElse(null);
    }
}
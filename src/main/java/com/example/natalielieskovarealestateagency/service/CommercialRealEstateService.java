package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommercialRealEstateService {
    CommercialRealEstateDTO createCommercialRealEstate(CommercialRealEstateDTO dto);

    CommercialRealEstateDTO findCommercialRealEstateById(Long id);

    PagedResponse<CommercialRealEstateDTO> findAllCommercialRealEstate(Pageable pageable);

    CommercialRealEstateDTO updateCommercialRealEstate(Long id, CommercialRealEstateDTO dto);

    void deleteCommercialRealEstate(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();

    CommercialRealEstateDTO getLastCreatedCommercialByUserId(Long adminCreatorId);
}
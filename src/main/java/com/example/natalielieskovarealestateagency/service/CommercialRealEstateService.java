package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;

import java.util.List;

public interface CommercialRealEstateService {
    CommercialRealEstateDTO createCommercialRealEstate(CommercialRealEstateDTO dto);

    CommercialRealEstateDTO findCommercialRealEstateById(Long id);

    List<CommercialRealEstateDTO> findAllCommercialRealEstate();

    CommercialRealEstateDTO updateCommercialRealEstate(Long id, CommercialRealEstateDTO dto);

    void deleteCommercialRealEstate(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();
}
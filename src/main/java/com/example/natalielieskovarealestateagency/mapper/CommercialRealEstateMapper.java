package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.model.CommercialRealEstate;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;

public class CommercialRealEstateMapper {
    public static CommercialRealEstateDTO toDTO(CommercialRealEstate entity) {
        String complexName = entity.getResidentialComplex() != null ? entity.getResidentialComplex().getName() : null;

        return new CommercialRealEstateDTO(
                entity.getId(),
                entity.getMicroDistrict(),
                entity.getAddress(),
                entity.getPrice(),
                entity.getCountOfRooms(),
                complexName,
                entity.getTotalArea(),
                entity.getFloor(),
                entity.getNumberOfStoreys(),
                entity.getCeilingHeight(),
                entity.getPropertyCondition(),
                entity.getPropertyDescription(),
                entity.getPhotoUrls()
        );
    }

    public static CommercialRealEstate toEntity(CommercialRealEstateDTO dto,
                                                ResidentialComplex complex) {
        return new CommercialRealEstate(
                dto.getId(),
                dto.getMicroDistrict(),
                dto.getAddress(),
                dto.getPrice(),
                dto.getCountOfRooms(),
                complex,
                dto.getTotalArea(),
                dto.getFloor(),
                dto.getNumberOfStoreys(),
                dto.getCeilingHeight(),
                dto.getPropertyCondition(),
                dto.getPropertyDescription(),
                dto.getPhotoUrls()
        );
    }
}

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
                entity.getResidentialComplex() != null ? entity.getResidentialComplex().getAddress() : null,
                entity.getPrice(),
                entity.getCountOfRooms(),
                complexName,
                entity.getResidentialComplex() != null ? entity.getResidentialComplex().getId() : null,
                entity.getTotalArea(),
                entity.getFloor(),
                entity.getResidentialComplex() != null ? entity.getResidentialComplex().getNumberOfStoreys() : null,
                entity.getCeilingHeight(),
                entity.getPropertyCondition(),
                entity.getOwnerPhoneNumber(),
                entity.getPropertyDescription(),
                entity.getAdminCreatorId(),
                entity.getPhotoUrls()
        );
    }

    public static CommercialRealEstate toEntity(CommercialRealEstateDTO dto,
                                                ResidentialComplex complex) {
        return new CommercialRealEstate(
                dto.getId(),
                dto.getMicroDistrict(),
                complex.getAddress(),
                dto.getPrice(),
                dto.getCountOfRooms(),
                complex,
                dto.getTotalArea(),
                dto.getFloor(),
                complex.getNumberOfStoreys(),
                dto.getCeilingHeight(),
                dto.getPropertyCondition(),
                dto.getOwnerPhoneNumber(),
                dto.getPropertyDescription(),
                dto.getAdminCreatorId(),
                dto.getPhotoUrls()
        );
    }
}

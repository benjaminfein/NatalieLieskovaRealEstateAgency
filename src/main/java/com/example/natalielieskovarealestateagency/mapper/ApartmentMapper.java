package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;
import com.example.natalielieskovarealestateagency.model.Apartment;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;

public class ApartmentMapper {
    public static ApartmentDTO maptoApartmentDTO(Apartment apartment) {
        return new ApartmentDTO(
                apartment.getId(),
                apartment.getMicroDistrict(),
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getAddress() : null,
                apartment.getPrice(),
                apartment.getCountOfRooms(),
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getName() : null,
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getId() : null,
                apartment.getTotalArea(),
                apartment.getLivingArea(),
                apartment.getKitchenArea(),
                apartment.getFloor(),
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getNumberOfStoreys() : null,
                apartment.getCeilingHeight(),
                apartment.getPropertyCondition(),
                apartment.getHeating(),
                apartment.getOwnerPhoneNumber(),
                apartment.getVideoUrl(),
                apartment.getPropertyDescription(),
                apartment.getAdminCreatorId(),
                apartment.getPhotoUrls()
        );
    }

    public static Apartment maptoApartment(ApartmentDTO apartmentDTO, ResidentialComplex complex) {
        return new Apartment(
                apartmentDTO.getId(),
                apartmentDTO.getMicroDistrict(),
                complex.getAddress(),
                apartmentDTO.getPrice(),
                apartmentDTO.getCountOfRooms(),
                complex,
                apartmentDTO.getTotalArea(),
                apartmentDTO.getLivingArea(),
                apartmentDTO.getKitchenArea(),
                apartmentDTO.getFloor(),
                complex.getNumberOfStoreys(),
                apartmentDTO.getCeilingHeight(),
                apartmentDTO.getPropertyCondition(),
                apartmentDTO.getHeating(),
                apartmentDTO.getOwnerPhoneNumber(),
                apartmentDTO.getVideoUrl(),
                apartmentDTO.getPropertyDescription(),
                apartmentDTO.getAdminCreatorId(),
                apartmentDTO.getPhotoUrls()
        );
    }

    public static ApartmentCardDTO mapToCardDTO(Apartment apartment) {
        return new ApartmentCardDTO(
                apartment.getId(),
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getAddress() : null,
                apartment.getPrice(),
                apartment.getCountOfRooms(),
                apartment.getTotalArea(),
                apartment.getLivingArea(),
                apartment.getKitchenArea(),
                apartment.getAdminCreatorId(),
                apartment.getPhotoUrls()
        );
    }

    public static Apartment mapToApartmentFromCardDTO(ApartmentCardDTO apartmentCardDTO) {
        return new Apartment(
                apartmentCardDTO.getId(),
                apartmentCardDTO.getAddress(),
                apartmentCardDTO.getPrice(),
                apartmentCardDTO.getCountOfRooms(),
                apartmentCardDTO.getTotalArea(),
                apartmentCardDTO.getLivingArea(),
                apartmentCardDTO.getKitchenArea(),
                apartmentCardDTO.getAdminCreatorId(),
                apartmentCardDTO.getPhotoUrls()
        );
    }
}

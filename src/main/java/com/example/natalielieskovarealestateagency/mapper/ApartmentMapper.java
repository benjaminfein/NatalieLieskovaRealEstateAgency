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
                apartment.getAddress(),
                apartment.getPrice(),
                apartment.getCountOfRooms(),
                apartment.getResidentialComplex() != null ? apartment.getResidentialComplex().getName() : null,
                apartment.getTotalArea(),
                apartment.getLivingArea(),
                apartment.getKitchenArea(),
                apartment.getFloor(),
                apartment.getNumberOfStoreys(),
                apartment.getCeilingHeight(),
                apartment.getPropertyCondition(),
                apartment.getHeating(),
                apartment.getPropertyDescription(),
                apartment.getPhotoUrls()
        );
    }

    public static Apartment maptoApartment(ApartmentDTO apartmentDTO, ResidentialComplex complex) {
        return new Apartment(
                apartmentDTO.getId(),
                apartmentDTO.getMicroDistrict(),
                apartmentDTO.getAddress(),
                apartmentDTO.getPrice(),
                apartmentDTO.getCountOfRooms(),
                complex,
                apartmentDTO.getTotalArea(),
                apartmentDTO.getLivingArea(),
                apartmentDTO.getKitchenArea(),
                apartmentDTO.getFloor(),
                apartmentDTO.getNumberOfStoreys(),
                apartmentDTO.getCeilingHeight(),
                apartmentDTO.getPropertyCondition(),
                apartmentDTO.getHeating(),
                apartmentDTO.getPropertyDescription(),
                apartmentDTO.getPhotoUrls()
        );
    }

    public static ApartmentCardDTO mapToCardDTO(Apartment apartment) {
        return new ApartmentCardDTO(
                apartment.getId(),
                apartment.getAddress(),
                apartment.getPrice(),
                apartment.getCountOfRooms(),
                apartment.getTotalArea(),
                apartment.getLivingArea(),
                apartment.getKitchenArea(),
                apartment.getPhotoUrls()
        );
    }
}

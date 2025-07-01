package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;

import java.util.List;
import java.util.stream.Collectors;

public class ResidentialComplexMapper {
    public static ResidentialComplexDTO maptoResidentialComplexDTO(ResidentialComplex residentialComplex) {
        List<ApartmentCardDTO> apartmentCards = residentialComplex.getApartments()
                .stream()
                .map(ApartmentMapper::mapToCardDTO)
                .collect(Collectors.toList());

        return new ResidentialComplexDTO(
                residentialComplex.getId(),
                residentialComplex.getName(),
                residentialComplex.getDistrict(),
                residentialComplex.getPrice(),
                residentialComplex.getDeveloper(),
                residentialComplex.getPromotion(),
                residentialComplex.getCompletedOrNot(),
                apartmentCards
        );
    }

    public static ResidentialComplex maptoResidentialComplex(ResidentialComplexDTO residentialComplexDTO) {
        return new ResidentialComplex(
                residentialComplexDTO.getId(),
                residentialComplexDTO.getName(),
                residentialComplexDTO.getDistrict(),
                residentialComplexDTO.getPrice(),
                residentialComplexDTO.getDeveloper(),
                residentialComplexDTO.getPromotion(),
                residentialComplexDTO.getCompletedOrNot()
        );
    }
}

package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.AreaRangeDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexWithApartmentsDTO;
import com.example.natalielieskovarealestateagency.model.AreaRange;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;

import java.util.stream.Collectors;

public class ResidentialComplexMapper {
    public static ResidentialComplexDTO maptoResidentialComplexDTO(ResidentialComplex residentialComplex) {
        return new ResidentialComplexDTO(
                residentialComplex.getId(),
                residentialComplex.getName(),
                residentialComplex.getDistrict(),
                residentialComplex.getAddress(),
                residentialComplex.getPrice(),
                residentialComplex.getDeveloper(),
                residentialComplex.getPromotionHeader(),
                residentialComplex.getPromotionText(),
                residentialComplex.getCompletedOrNot(),
                residentialComplex.getNumberOfStoreys(),
                toAreaRangeDTO(residentialComplex.getOneRoom()),
                toAreaRangeDTO(residentialComplex.getTwoRoom()),
                toAreaRangeDTO(residentialComplex.getThreeRoom()),
                toAreaRangeDTO(residentialComplex.getFourRoom()),
                toAreaRangeDTO(residentialComplex.getFiveRoom()),
                residentialComplex.getVideoUrl(),
                residentialComplex.getDescription(),
                residentialComplex.getPhotoUrls()
        );
    }

    public static ResidentialComplex maptoResidentialComplex(ResidentialComplexDTO residentialComplexDTO) {
        return new ResidentialComplex(
                residentialComplexDTO.getId(),
                residentialComplexDTO.getName(),
                residentialComplexDTO.getDistrict(),
                residentialComplexDTO.getAddress(),
                residentialComplexDTO.getPrice(),
                residentialComplexDTO.getDeveloper(),
                residentialComplexDTO.getPromotionHeader(),
                residentialComplexDTO.getPromotionText(),
                residentialComplexDTO.getNumberOfStoreys(),
                toAreaRange(residentialComplexDTO.getOneRoom()),
                toAreaRange(residentialComplexDTO.getTwoRoom()),
                toAreaRange(residentialComplexDTO.getThreeRoom()),
                toAreaRange(residentialComplexDTO.getFourRoom()),
                toAreaRange(residentialComplexDTO.getFiveRoom()),
                residentialComplexDTO.getVideoUrl(),
                residentialComplexDTO.getDescription(),
                residentialComplexDTO.getCompletedOrNot()
        );
    }

    public static ResidentialComplexWithApartmentsDTO mapToResidentialComplexWithApartmentsDTO(
            ResidentialComplex residentialComplex
    ) {
        return new ResidentialComplexWithApartmentsDTO(
                residentialComplex.getId(),
                residentialComplex.getName(),
                residentialComplex.getDistrict(),
                residentialComplex.getAddress(),
                residentialComplex.getPrice(),
                residentialComplex.getDeveloper(),
                residentialComplex.getPromotionHeader(),
                residentialComplex.getPromotionText(),
                residentialComplex.getNumberOfStoreys(),
                residentialComplex.getCompletedOrNot(),
                toAreaRangeDTO(residentialComplex.getOneRoom()),
                toAreaRangeDTO(residentialComplex.getTwoRoom()),
                toAreaRangeDTO(residentialComplex.getThreeRoom()),
                toAreaRangeDTO(residentialComplex.getFourRoom()),
                toAreaRangeDTO(residentialComplex.getFiveRoom()),
                residentialComplex.getVideoUrl(),
                residentialComplex.getDescription(),
                residentialComplex.getPhotoUrls(),
                residentialComplex.getApartments().stream()
                        .map(ApartmentMapper::mapToCardDTO)
                        .collect(Collectors.toList())
        );
    }

    public static ResidentialComplex maptoResidentialComplex(
            ResidentialComplexWithApartmentsDTO residentialComplexWithApartmentsDTO
    ) {
        return new ResidentialComplex(
                residentialComplexWithApartmentsDTO.getId(),
                residentialComplexWithApartmentsDTO.getName(),
                residentialComplexWithApartmentsDTO.getDistrict(),
                residentialComplexWithApartmentsDTO.getAddress(),
                residentialComplexWithApartmentsDTO.getPrice(),
                residentialComplexWithApartmentsDTO.getDeveloper(),
                residentialComplexWithApartmentsDTO.getPromotionHeader(),
                residentialComplexWithApartmentsDTO.getPromotionText(),
                residentialComplexWithApartmentsDTO.getNumberOfStoreys(),
                toAreaRange(residentialComplexWithApartmentsDTO.getOneRoom()),
                toAreaRange(residentialComplexWithApartmentsDTO.getTwoRoom()),
                toAreaRange(residentialComplexWithApartmentsDTO.getThreeRoom()),
                toAreaRange(residentialComplexWithApartmentsDTO.getFourRoom()),
                toAreaRange(residentialComplexWithApartmentsDTO.getFiveRoom()),
                residentialComplexWithApartmentsDTO.getVideoUrl(),
                residentialComplexWithApartmentsDTO.getDescription(),
                residentialComplexWithApartmentsDTO.getApartments().stream()
                        .map(ApartmentMapper::mapToApartmentFromCardDTO)
                        .collect(Collectors.toList()),
                residentialComplexWithApartmentsDTO.getCompletedOrNot()
        );
    }

    private static AreaRangeDTO toAreaRangeDTO(AreaRange areaRange) {
        if (areaRange == null) return null;
        return new AreaRangeDTO(areaRange.getMin(), areaRange.getMax());
    }

    private static AreaRange toAreaRange(AreaRangeDTO areaRangeDTO) {
        if (areaRangeDTO == null) return null;
        return new AreaRange(areaRangeDTO.getMin(), areaRangeDTO.getMax());
    }
}

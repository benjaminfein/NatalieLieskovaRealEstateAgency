package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.AreaRangeDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.model.AreaRange;
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
                residentialComplex.getPromotionHeader(),
                residentialComplex.getPromotionText(),
                residentialComplex.getCompletedOrNot(),
                toAreaRangeDTO(residentialComplex.getOneRoom()),
                toAreaRangeDTO(residentialComplex.getTwoRoom()),
                toAreaRangeDTO(residentialComplex.getThreeRoom()),
                toAreaRangeDTO(residentialComplex.getFourRoom()),
                toAreaRangeDTO(residentialComplex.getFiveRoom()),
                apartmentCards,
                residentialComplex.getPhotoUrls()
        );
    }

    public static ResidentialComplex maptoResidentialComplex(ResidentialComplexDTO residentialComplexDTO) {
        return new ResidentialComplex(
                residentialComplexDTO.getId(),
                residentialComplexDTO.getName(),
                residentialComplexDTO.getDistrict(),
                residentialComplexDTO.getPrice(),
                residentialComplexDTO.getDeveloper(),
                residentialComplexDTO.getPromotionHeader(),
                residentialComplexDTO.getPromotionText(),
                toAreaRange(residentialComplexDTO.getOneRoom()),
                toAreaRange(residentialComplexDTO.getTwoRoom()),
                toAreaRange(residentialComplexDTO.getThreeRoom()),
                toAreaRange(residentialComplexDTO.getFourRoom()),
                toAreaRange(residentialComplexDTO.getFiveRoom()),
                residentialComplexDTO.getCompletedOrNot()
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

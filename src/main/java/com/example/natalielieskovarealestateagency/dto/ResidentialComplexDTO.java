package com.example.natalielieskovarealestateagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentialComplexDTO {
    private Long id;
    private String name;
    private String district;
    private String price;
    private String developer;
    private String promotionHeader;
    private String promotionText;
    private Boolean completedOrNot;

    private AreaRangeDTO oneRoom;
    private AreaRangeDTO twoRoom;
    private AreaRangeDTO threeRoom;
    private AreaRangeDTO fourRoom;
    private AreaRangeDTO fiveRoom;

    private List<ApartmentCardDTO> apartments;
    private List<String> photoUrls;
}

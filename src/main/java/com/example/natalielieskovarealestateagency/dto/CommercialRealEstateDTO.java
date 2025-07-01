package com.example.natalielieskovarealestateagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommercialRealEstateDTO {
    private Long id;
    private String microDistrict;
    private String address;
    private Integer price;
    private String residentialComplexName;
    private Float totalArea;
    private Integer floor;
    private Integer numberOfStoreys;
    private Integer ceilingHeight;
    private String propertyCondition;
    private String propertyDescription;
}
package com.example.natalielieskovarealestateagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDTO {
    private Long id;
    private String microDistrict;
    private String address;
    private Integer price;
    private Integer countOfRooms;
    private String nameOfResidentialComplex;
    private Float totalArea;
    private Float livingArea;
    private Float kitchenArea;
    private Integer floor;
    private Integer numberOfStoreys;
    private Integer ceilingHeight;
    private String propertyCondition;
    private String heating;
    private String propertyDescription;
}

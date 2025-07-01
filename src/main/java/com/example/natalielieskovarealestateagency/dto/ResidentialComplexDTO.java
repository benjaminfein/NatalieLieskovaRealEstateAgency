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
    private Integer price;
    private String developer;
    private String promotion;
    private Boolean completedOrNot;
    private List<ApartmentCardDTO> apartments;
}
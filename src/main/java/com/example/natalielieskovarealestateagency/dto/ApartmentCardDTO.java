package com.example.natalielieskovarealestateagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentCardDTO {
    private Long id;
    private String address;
    private Integer price;
    private Integer countOfRooms;
    private Float totalArea;
    private Float livingArea;
    private Float kitchenArea;
    private String adminCreator;
    private List<String> photoUrls;
}

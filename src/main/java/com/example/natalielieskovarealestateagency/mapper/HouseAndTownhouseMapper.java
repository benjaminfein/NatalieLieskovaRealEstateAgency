package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.HouseAndTownhouseDTO;
import com.example.natalielieskovarealestateagency.model.HouseAndTownhouse;

public class HouseAndTownhouseMapper {
    public static HouseAndTownhouseDTO maptoHouseAndTownhouseDTO(HouseAndTownhouse houseAndTownhouse) {
        return new HouseAndTownhouseDTO(
                houseAndTownhouse.getId(),
                houseAndTownhouse.getType(),
                houseAndTownhouse.getMicroDistrict(),
                houseAndTownhouse.getAddress(),
                houseAndTownhouse.getPrice(),
                houseAndTownhouse.getCountOfRooms(),
                houseAndTownhouse.getNameOfResidentialComplex(),
                houseAndTownhouse.getTotalArea(),
                houseAndTownhouse.getLivingArea(),
                houseAndTownhouse.getKitchenArea(),
                houseAndTownhouse.getFloor(),
                houseAndTownhouse.getNumberOfStoreys(),
                houseAndTownhouse.getCeilingHeight(),
                houseAndTownhouse.getPropertyCondition(),
                houseAndTownhouse.getHeating(),
                houseAndTownhouse.getPropertyDescription(),
                houseAndTownhouse.getPhotoUrls()
        );
    }

    public static HouseAndTownhouse maptoHouseAndTownhouse(HouseAndTownhouseDTO houseAndTownhouseDTO) {
        return new HouseAndTownhouse(
                houseAndTownhouseDTO.getId(),
                houseAndTownhouseDTO.getType(),
                houseAndTownhouseDTO.getMicroDistrict(),
                houseAndTownhouseDTO.getAddress(),
                houseAndTownhouseDTO.getPrice(),
                houseAndTownhouseDTO.getCountOfRooms(),
                houseAndTownhouseDTO.getNameOfResidentialComplex(),
                houseAndTownhouseDTO.getTotalArea(),
                houseAndTownhouseDTO.getLivingArea(),
                houseAndTownhouseDTO.getKitchenArea(),
                houseAndTownhouseDTO.getFloor(),
                houseAndTownhouseDTO.getNumberOfStoreys(),
                houseAndTownhouseDTO.getCeilingHeight(),
                houseAndTownhouseDTO.getPropertyCondition(),
                houseAndTownhouseDTO.getHeating(),
                houseAndTownhouseDTO.getPropertyDescription(),
                houseAndTownhouseDTO.getPhotoUrls()
        );
    }
}

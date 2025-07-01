package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.HouseAndTownhouseDTO;

import java.util.List;

public interface HouseAndTownhouseService {
    HouseAndTownhouseDTO createHouseAndTownhouse(HouseAndTownhouseDTO houseAndTownhouseDTO);

    HouseAndTownhouseDTO getHouseAndTownhouseById(Long id);

    List<HouseAndTownhouseDTO> getAllHouseAndTownhouse();

    HouseAndTownhouseDTO updateHouseAndTownhouse(Long id, HouseAndTownhouseDTO houseAndTownhouseDTO);

    void deleteHouseAndTownhouse(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();
}

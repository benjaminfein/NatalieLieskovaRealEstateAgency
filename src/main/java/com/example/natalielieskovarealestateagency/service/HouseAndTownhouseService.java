package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.HouseAndTownhouseDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HouseAndTownhouseService {
    HouseAndTownhouseDTO createHouseAndTownhouse(HouseAndTownhouseDTO houseAndTownhouseDTO);

    HouseAndTownhouseDTO getHouseAndTownhouseById(Long id);

    PagedResponse<HouseAndTownhouseDTO> getAllHouseAndTownhouse(Pageable pageable);

    HouseAndTownhouseDTO updateHouseAndTownhouse(Long id, HouseAndTownhouseDTO houseAndTownhouseDTO);

    void deleteHouseAndTownhouse(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();

    HouseAndTownhouseDTO getLastCreatedHouseOrTownhouseByUserId(Long adminCreatorId);
}

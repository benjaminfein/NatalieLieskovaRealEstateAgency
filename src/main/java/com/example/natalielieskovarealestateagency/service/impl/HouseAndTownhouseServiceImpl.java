package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.HouseAndTownhouseDTO;
import com.example.natalielieskovarealestateagency.exception.HouseAndTownhouseNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.HouseAndTownhouseMapper;
import com.example.natalielieskovarealestateagency.model.HouseAndTownhouse;
import com.example.natalielieskovarealestateagency.repository.HouseAndTownhouseRepository;
import com.example.natalielieskovarealestateagency.service.HouseAndTownhouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HouseAndTownhouseServiceImpl implements HouseAndTownhouseService {
    private HouseAndTownhouseRepository houseAndTownhouseRepository;

    @Override
    public HouseAndTownhouseDTO createHouseAndTownhouse(HouseAndTownhouseDTO houseAndTownhouseDTO) {
        HouseAndTownhouse houseAndTownhouse = HouseAndTownhouseMapper.maptoHouseAndTownhouse(houseAndTownhouseDTO);
        HouseAndTownhouse savedHouseAndTownhouse = houseAndTownhouseRepository.save(houseAndTownhouse);
        return HouseAndTownhouseMapper.maptoHouseAndTownhouseDTO(savedHouseAndTownhouse);
    }

    @Override
    public HouseAndTownhouseDTO getHouseAndTownhouseById(Long id) {
        HouseAndTownhouse houseAndTownhouse = houseAndTownhouseRepository.findById(id)
                .orElseThrow(() -> new HouseAndTownhouseNotFoundException("House and townhouse is not exist with given id" + id));

        return HouseAndTownhouseMapper.maptoHouseAndTownhouseDTO(houseAndTownhouse);
    }

    @Override
    public List<HouseAndTownhouseDTO> getAllHouseAndTownhouse() {
        List<HouseAndTownhouse> houseAndTownhouseList = houseAndTownhouseRepository.findAll();
        return houseAndTownhouseList.stream().map(HouseAndTownhouseMapper::maptoHouseAndTownhouseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HouseAndTownhouseDTO updateHouseAndTownhouse(Long id, HouseAndTownhouseDTO houseAndTownhouseToUpdate) {
        HouseAndTownhouse houseAndTownhouse = houseAndTownhouseRepository.findById(id).orElseThrow(
                () -> new HouseAndTownhouseNotFoundException("House and townhouse is not exist with given id: " + id));

        houseAndTownhouse.setType(houseAndTownhouseToUpdate.getType());
        houseAndTownhouse.setMicroDistrict(houseAndTownhouseToUpdate.getMicroDistrict());
        houseAndTownhouse.setAddress(houseAndTownhouseToUpdate.getAddress());
        houseAndTownhouse.setPrice(houseAndTownhouseToUpdate.getPrice());
        houseAndTownhouse.setCountOfRooms(houseAndTownhouseToUpdate.getCountOfRooms());
        houseAndTownhouse.setNameOfResidentialComplex(houseAndTownhouseToUpdate.getNameOfResidentialComplex());
        houseAndTownhouse.setTotalArea(houseAndTownhouseToUpdate.getTotalArea());
        houseAndTownhouse.setLivingArea(houseAndTownhouseToUpdate.getLivingArea());
        houseAndTownhouse.setKitchenArea(houseAndTownhouseToUpdate.getKitchenArea());
        houseAndTownhouse.setFloor(houseAndTownhouseToUpdate.getFloor());
        houseAndTownhouse.setNumberOfStoreys(houseAndTownhouseToUpdate.getNumberOfStoreys());
        houseAndTownhouse.setCeilingHeight(houseAndTownhouseToUpdate.getCeilingHeight());
        houseAndTownhouse.setPropertyCondition(houseAndTownhouseToUpdate.getPropertyCondition());
        houseAndTownhouse.setHeating(houseAndTownhouseToUpdate.getHeating());
        houseAndTownhouse.setOwnerPhoneNumber(houseAndTownhouseToUpdate.getOwnerPhoneNumber());
        houseAndTownhouse.setPropertyDescription(houseAndTownhouseToUpdate.getPropertyDescription());
        HouseAndTownhouse savedHouseAndTownhouse = houseAndTownhouseRepository.save(houseAndTownhouse);
        return HouseAndTownhouseMapper.maptoHouseAndTownhouseDTO(savedHouseAndTownhouse);
    }

    @Override
    public void deleteHouseAndTownhouse(Long id) {
        HouseAndTownhouse houseAndTownhouse = houseAndTownhouseRepository.findById(id).orElseThrow(
                () -> new HouseAndTownhouseNotFoundException("House and townhouse is not exist with given id: " + id)
        );
        houseAndTownhouseRepository.deleteById(id);
    }

    @Override
    public List<String> getAllDistricts() {
        return houseAndTownhouseRepository.findAllDistinctMicroDistricts();
    }

    @Override
    public List<Integer> getAllRoomCounts() {
        return houseAndTownhouseRepository.findAllDistinctRoomCounts();
    }
}

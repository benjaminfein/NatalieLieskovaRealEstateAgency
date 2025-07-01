package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.dto.HouseAndTownhouseDTO;
import com.example.natalielieskovarealestateagency.service.HouseAndTownhouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/house-and-townhouse")
public class HouseAndTownhouseController {
    private HouseAndTownhouseService houseAndTownhouseService;

    @PostMapping
    public ResponseEntity<HouseAndTownhouseDTO> createHouseAndTownhouse(@RequestBody HouseAndTownhouseDTO houseAndTownhouseDTO) {
        HouseAndTownhouseDTO savedHouseAndTownhouse = houseAndTownhouseService.createHouseAndTownhouse(houseAndTownhouseDTO);
        return new ResponseEntity<>(savedHouseAndTownhouse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<HouseAndTownhouseDTO> getHouseAndTownhouseById(@PathVariable Long id) {
        HouseAndTownhouseDTO houseAndTownhouseDTO = houseAndTownhouseService.getHouseAndTownhouseById(id);
        return ResponseEntity.ok(houseAndTownhouseDTO);
    }

    @GetMapping
    public ResponseEntity<List<HouseAndTownhouseDTO>> getAllHouseAndTownhouse() {
        List<HouseAndTownhouseDTO> houseAndTownhouse = houseAndTownhouseService.getAllHouseAndTownhouse();
        return ResponseEntity.ok(houseAndTownhouse);
    }

    @PutMapping("{id}")
    public ResponseEntity<HouseAndTownhouseDTO> updateHouseAndTownhouse(@PathVariable("id") Long id,
                                                                          @RequestBody HouseAndTownhouseDTO houseAndTownhouseToUpdate) {
        HouseAndTownhouseDTO houseAndTownhouseDTO = houseAndTownhouseService
                .updateHouseAndTownhouse(id, houseAndTownhouseToUpdate);
        return ResponseEntity.ok(houseAndTownhouseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteHouseAndTownhouse(@PathVariable("id") Long id) {
        houseAndTownhouseService.deleteHouseAndTownhouse(id);
        return ResponseEntity.ok("House and townhouse complex deleted successfully!");
    }

    @GetMapping("/get-all-districts")
    public ResponseEntity<List<String>> getAllDistricts() {
        List<String> districts = houseAndTownhouseService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/get-all-amounts-of-rooms")
    public ResponseEntity<List<Integer>> getAllRoomCounts() {
        List<Integer> counts = houseAndTownhouseService.getAllRoomCounts();
        return ResponseEntity.ok(counts);
    }
}

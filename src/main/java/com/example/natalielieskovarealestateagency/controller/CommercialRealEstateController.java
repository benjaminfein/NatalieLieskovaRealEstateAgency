package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.service.CommercialRealEstateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commercial")
@AllArgsConstructor
public class CommercialRealEstateController {

    private final CommercialRealEstateService commercialRealEstateService;

    @PostMapping
    public ResponseEntity<CommercialRealEstateDTO> create(@RequestBody CommercialRealEstateDTO dto) {
        return new ResponseEntity<>(commercialRealEstateService.createCommercialRealEstate(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommercialRealEstateDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commercialRealEstateService.findCommercialRealEstateById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommercialRealEstateDTO>> getAll() {
        return ResponseEntity.ok(commercialRealEstateService.findAllCommercialRealEstate());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommercialRealEstateDTO> update(@PathVariable Long id,
                                                          @RequestBody CommercialRealEstateDTO dto) {
        return ResponseEntity.ok(commercialRealEstateService.updateCommercialRealEstate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        commercialRealEstateService.deleteCommercialRealEstate(id);
        return ResponseEntity.ok("Commercial real estate deleted successfully!");
    }

    @GetMapping("/get-all-districts")
    public ResponseEntity<List<String>> getAllDistricts() {
        List<String> districts = commercialRealEstateService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/get-all-amounts-of-rooms")
    public ResponseEntity<List<Integer>> getAllRoomCounts() {
        List<Integer> counts = commercialRealEstateService.getAllRoomCounts();
        return ResponseEntity.ok(counts);
    }
}
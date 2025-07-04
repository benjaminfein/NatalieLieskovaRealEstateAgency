package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.service.CommercialRealEstateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/commercial")
@AllArgsConstructor
public class CommercialRealEstateController {
    private final CommercialRealEstateService commercialRealEstateService;

    @PostMapping
    public ResponseEntity<CommercialRealEstateDTO> createCommercial(@RequestBody CommercialRealEstateDTO dto) {
        return new ResponseEntity<>(commercialRealEstateService.createCommercialRealEstate(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommercialRealEstateDTO> getCommercialById(@PathVariable Long id) {
        return ResponseEntity.ok(commercialRealEstateService.findCommercialRealEstateById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CommercialRealEstateDTO>> getAllCommercials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<CommercialRealEstateDTO> response = commercialRealEstateService.findAllCommercialRealEstate(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommercialRealEstateDTO> updateCommercial(@PathVariable Long id,
                                                          @RequestBody CommercialRealEstateDTO dto) {
        return ResponseEntity.ok(commercialRealEstateService.updateCommercialRealEstate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommercial(@PathVariable Long id) {
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
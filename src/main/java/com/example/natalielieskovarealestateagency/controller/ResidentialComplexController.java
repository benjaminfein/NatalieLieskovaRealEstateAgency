package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.dto.CommercialRealEstateDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.service.ResidentialComplexService;
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
@RequestMapping("/api/residential-complex")
@AllArgsConstructor
public class ResidentialComplexController {
    private ResidentialComplexService residentialComplexService;

    @PostMapping
    public ResponseEntity<ResidentialComplexDTO> createResidentialComplex(
            @RequestBody ResidentialComplexDTO residentialComplexDTO
    ) {
        ResidentialComplexDTO savedResidentialComplex = residentialComplexService
                .createResidentialComplex(residentialComplexDTO);
        return new ResponseEntity<>(savedResidentialComplex, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResidentialComplexDTO> getResidentialComplexById(@PathVariable Long id) {
        ResidentialComplexDTO residentialComplexDTO = residentialComplexService.getResidentialComplexById(id);
        return ResponseEntity.ok(residentialComplexDTO);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ResidentialComplexDTO>> getAllResidentialComplexes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<ResidentialComplexDTO> response = residentialComplexService.getAllResidentialComplexes(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResidentialComplexDTO> updateResidentialComplex(@PathVariable("id") Long id,
                                                                          @RequestBody ResidentialComplexDTO residentialComplexToUpdate) {
        ResidentialComplexDTO residentialComplexDTO = residentialComplexService
                .updateResidentialComplex(id, residentialComplexToUpdate);
        return ResponseEntity.ok(residentialComplexDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteResidentialComplex(@PathVariable("id") Long id) {
        residentialComplexService.deleteResidentialComplex(id);
        return ResponseEntity.ok("Residential complex deleted successfully!");
    }
}

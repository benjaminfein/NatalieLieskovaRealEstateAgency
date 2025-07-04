package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.service.ApartmentService;
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
@RequestMapping("/api/apartment")
@AllArgsConstructor
public class ApartmentController {
    private ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentDTO apartmentDTO) {
        ApartmentDTO savedApartment = apartmentService.createApartment(apartmentDTO);
        return new ResponseEntity<>(savedApartment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApartmentDTO> getApartmentById(@PathVariable Long id) {
        ApartmentDTO apartmentDTO = apartmentService.getApartmentById(id);
        return ResponseEntity.ok(apartmentDTO);
    }

    @GetMapping("{complexId}")
    public ResponseEntity<List<ApartmentCardDTO>> getApartmentsByComplex(@PathVariable Long complexId) {
        List<ApartmentCardDTO> apartmentCardDTOs = apartmentService.getApartmentsByComplex(complexId);
        return ResponseEntity.ok(apartmentCardDTOs);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ApartmentDTO>> getAllApartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponse<ApartmentDTO> response = apartmentService.getAllApartments(pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cards")
    public ResponseEntity<List<ApartmentCardDTO>> getAllApartmentCards() {
        List<ApartmentCardDTO> apartmentCards = apartmentService.getAllApartmentCards();
        return ResponseEntity.ok(apartmentCards);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable("id") Long id,
                                                                          @RequestBody ApartmentDTO apartmentToUpdate) {
        ApartmentDTO apartmentDTO = apartmentService
                .updateApartment(id, apartmentToUpdate);
        return ResponseEntity.ok(apartmentDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok("Apartment deleted successfully!");
    }

    @GetMapping("/get-all-districts")
    public ResponseEntity<List<String>> getAllDistricts() {
        List<String> districts = apartmentService.getAllDistricts();
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/get-all-amounts-of-rooms")
    public ResponseEntity<List<Integer>> getAllRoomCounts() {
        List<Integer> counts = apartmentService.getAllRoomCounts();
        return ResponseEntity.ok(counts);
    }
}

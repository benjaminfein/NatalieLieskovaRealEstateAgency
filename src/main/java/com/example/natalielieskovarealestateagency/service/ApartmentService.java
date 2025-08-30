package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;
import com.example.natalielieskovarealestateagency.model.Apartment;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO createApartment(ApartmentDTO apartmentDTO);

    ApartmentDTO getApartmentById(Long id);

    List<ApartmentCardDTO> getApartmentsByComplex(Long complexId);

    PagedResponse<ApartmentDTO> getAllApartments(Pageable pageable);

    List<ApartmentCardDTO> getAllApartmentCards();

    ApartmentDTO updateApartment(Long id, ApartmentDTO apartmentDTO);

    void deleteApartment(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();

    ApartmentDTO getLastCreatedApartmentByUserId(Long adminCreatorId);
}

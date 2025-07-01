package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO createApartment(ApartmentDTO apartmentDTO);

    ApartmentDTO getApartmentById(Long id);

    List<ApartmentCardDTO> getApartmentsByComplex(Long complexId);

    List<ApartmentDTO> getAllApartment();

    List<ApartmentCardDTO> getAllApartmentCards();

    ApartmentDTO updateApartment(Long id, ApartmentDTO apartmentDTO);

    void deleteApartment(Long id);

    List<String> getAllDistricts();

    List<Integer> getAllRoomCounts();
}

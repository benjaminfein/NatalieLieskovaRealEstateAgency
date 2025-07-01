package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;
import com.example.natalielieskovarealestateagency.exception.ApartmentNotFoundException;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.ApartmentMapper;
import com.example.natalielieskovarealestateagency.model.Apartment;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.ApartmentRepository;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.ApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private ApartmentRepository apartmentRepository;
    private ResidentialComplexRepository residentialComplexRepository;

    @Override
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        ResidentialComplex complex = residentialComplexRepository.findByName(apartmentDTO.getNameOfResidentialComplex())
                .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));

        Apartment apartment = ApartmentMapper.maptoApartment(apartmentDTO, complex);
        Apartment savedApartment = apartmentRepository.save(apartment);
        return ApartmentMapper.maptoApartmentDTO(savedApartment);
    }

    @Override
    public ApartmentDTO getApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment is not exist with given id" + id));

        return ApartmentMapper.maptoApartmentDTO(apartment);
    }

    @Override
    public List<ApartmentCardDTO> getApartmentsByComplex(Long complexId) {
        return apartmentRepository.findByResidentialComplexId(complexId).stream()
                .map(ApartmentMapper::mapToCardDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentDTO> getAllApartment() {
        List<Apartment> apartmentList = apartmentRepository.findAll();
        return apartmentList.stream().map(ApartmentMapper::maptoApartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApartmentCardDTO> getAllApartmentCards() {
        return apartmentRepository.findAll().stream()
                .map(ApartmentMapper::mapToCardDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApartmentDTO updateApartment(Long id, ApartmentDTO apartmentToUpdate) {
        Apartment updatedApartment = apartmentRepository.findById(id).orElseThrow(
                () -> new ApartmentNotFoundException("Apartment is not exist with given id: " + id));
        ResidentialComplex complex = residentialComplexRepository.findByName(apartmentToUpdate.getNameOfResidentialComplex())
                .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));

        updatedApartment.setMicroDistrict(apartmentToUpdate.getMicroDistrict());
        updatedApartment.setAddress(apartmentToUpdate.getAddress());
        updatedApartment.setPrice(apartmentToUpdate.getPrice());
        updatedApartment.setCountOfRooms(apartmentToUpdate.getCountOfRooms());
        updatedApartment.setResidentialComplex(complex);
        updatedApartment.setTotalArea(apartmentToUpdate.getTotalArea());
        updatedApartment.setLivingArea(apartmentToUpdate.getLivingArea());
        updatedApartment.setKitchenArea(apartmentToUpdate.getKitchenArea());
        updatedApartment.setFloor(apartmentToUpdate.getFloor());
        updatedApartment.setNumberOfStoreys(apartmentToUpdate.getNumberOfStoreys());
        updatedApartment.setCeilingHeight(apartmentToUpdate.getCeilingHeight());
        updatedApartment.setPropertyCondition(apartmentToUpdate.getPropertyCondition());
        updatedApartment.setHeating(apartmentToUpdate.getHeating());
        updatedApartment.setPropertyDescription(apartmentToUpdate.getPropertyDescription());
        Apartment saved = apartmentRepository.save(updatedApartment);
        return ApartmentMapper.maptoApartmentDTO(saved);
    }

    @Override
    public void deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(
                () -> new ApartmentNotFoundException("Apartment is not exist with given id: " + id)
        );
        apartmentRepository.deleteById(id);
    }

    @Override
    public List<String> getAllDistricts() {
        return apartmentRepository.findAllDistinctMicroDistricts();
    }

    @Override
    public List<Integer> getAllRoomCounts() {
        return apartmentRepository.findAllDistinctRoomCounts();
    }
}

package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ApartmentCardDTO;
import com.example.natalielieskovarealestateagency.dto.ApartmentDTO;
import com.example.natalielieskovarealestateagency.exception.ApartmentNotFoundException;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.ApartmentMapper;
import com.example.natalielieskovarealestateagency.model.Apartment;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.ApartmentRepository;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.ApartmentService;
import com.example.natalielieskovarealestateagency.service.ResidentialComplexService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private ApartmentRepository apartmentRepository;
    private ResidentialComplexRepository residentialComplexRepository;
    private ResidentialComplexService residentialComplexService;

    @Override
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        ResidentialComplex complex = residentialComplexRepository.findByName(apartmentDTO.getNameOfResidentialComplex())
                .orElseThrow(() -> new ResidentialComplexNotFoundException("ЖК не найден"));

        Apartment apartment = ApartmentMapper.maptoApartment(apartmentDTO, complex);
        Apartment savedApartment = apartmentRepository.save(apartment);
        residentialComplexService.updateAreaRanges(apartment.getResidentialComplex());
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
    public PagedResponse<ApartmentDTO> getAllApartments(Pageable pageable) {
        Page<Apartment> apartmentPage = apartmentRepository.findAll(pageable);

        List<ApartmentDTO> dtoList = apartmentPage.getContent()
                .stream()
                .map(ApartmentMapper::maptoApartmentDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                apartmentPage.getNumber(),
                apartmentPage.getSize(),
                apartmentPage.getTotalElements()
        );
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
        updatedApartment.setAddress(complex.getAddress());
        updatedApartment.setPrice(apartmentToUpdate.getPrice());
        updatedApartment.setCountOfRooms(apartmentToUpdate.getCountOfRooms());
        updatedApartment.setTotalArea(apartmentToUpdate.getTotalArea());
        updatedApartment.setLivingArea(apartmentToUpdate.getLivingArea());
        updatedApartment.setKitchenArea(apartmentToUpdate.getKitchenArea());
        updatedApartment.setFloor(apartmentToUpdate.getFloor());
        updatedApartment.setNumberOfStoreys(complex.getNumberOfStoreys());
        updatedApartment.setCeilingHeight(apartmentToUpdate.getCeilingHeight());
        updatedApartment.setPropertyCondition(apartmentToUpdate.getPropertyCondition());
        updatedApartment.setHeating(apartmentToUpdate.getHeating());
        updatedApartment.setOwnerPhoneNumber(apartmentToUpdate.getOwnerPhoneNumber());
        updatedApartment.setPropertyDescription(apartmentToUpdate.getPropertyDescription());
        updatedApartment.setAdminCreator(apartmentToUpdate.getAdminCreator());
        updatedApartment.setPhotoUrls(apartmentToUpdate.getPhotoUrls());

        ResidentialComplex oldComplex = updatedApartment.getResidentialComplex();
        updatedApartment.setResidentialComplex(complex);

        Apartment saved = apartmentRepository.save(updatedApartment);

        if (updatedApartment.getResidentialComplex() != null) {
            residentialComplexService.updateAreaRanges(updatedApartment.getResidentialComplex());
        }
        if (!oldComplex.getId().equals(complex.getId())) {
            residentialComplexService.updateAreaRanges(complex);
        }

        return ApartmentMapper.maptoApartmentDTO(saved);
    }

    @Override
    public void deleteApartment(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(
                () -> new ApartmentNotFoundException("Apartment is not exist with given id: " + id)
        );
        apartmentRepository.deleteById(id);
        if (apartment.getResidentialComplex() != null) {
            residentialComplexService.updateAreaRanges(apartment.getResidentialComplex());
        }
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

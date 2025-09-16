package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.dto.ResidentialComplexWithApartmentsDTO;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.ResidentialComplexMapper;
import com.example.natalielieskovarealestateagency.model.Apartment;
import com.example.natalielieskovarealestateagency.model.AreaRange;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.ResidentialComplexService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidentialComplexServiceImpl implements ResidentialComplexService {
    private ResidentialComplexRepository residentialComplexRepository;

    @Override
    public void updateAreaRanges(ResidentialComplex complex) {
        List<Apartment> apartments = complex.getApartments();

        Map<Integer, List<Float>> grouped = apartments.stream()
                .filter(a -> a.getTotalArea() != null)
                .collect(Collectors.groupingBy(
                        Apartment::getCountOfRooms,
                        Collectors.mapping(Apartment::getTotalArea, Collectors.toList())
                ));

        complex.setOneRoom(getRange(grouped.get(1)));
        complex.setTwoRoom(getRange(grouped.get(2)));
        complex.setThreeRoom(getRange(grouped.get(3)));
        complex.setFourRoom(getRange(grouped.get(4)));
        complex.setFiveRoom(getRange(grouped.get(5)));

        residentialComplexRepository.save(complex);
    }

    private AreaRange getRange(List<Float> areas) {
        if (areas == null || areas.isEmpty()) return null;
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;

        for (Float area : areas) {
            if (area == null) continue;
            if (area < min) min = area;
            if (area > max) max = area;
        }

        return new AreaRange(min, max);
    }

    @Override
    public ResidentialComplexDTO createResidentialComplex(ResidentialComplexDTO residentialComplexDTO) {
        ResidentialComplex residentialComplex = ResidentialComplexMapper.maptoResidentialComplex(residentialComplexDTO);
        ResidentialComplex savedResidentialComplex = residentialComplexRepository.save(residentialComplex);
        return ResidentialComplexMapper.maptoResidentialComplexDTO(savedResidentialComplex);
    }

    @Override
    public ResidentialComplexDTO getResidentialComplexById(Long id) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id)
                .orElseThrow(() -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id" + id));

        return ResidentialComplexMapper.maptoResidentialComplexDTO(residentialComplex);
    }

    @Override
    public ResidentialComplexWithApartmentsDTO getResidentialComplexWithApartmentsById(Long id) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id)
                .orElseThrow(() -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id" + id));

        return ResidentialComplexMapper.mapToResidentialComplexWithApartmentsDTO(residentialComplex);
    }

    @Override
    public PagedResponse<ResidentialComplexDTO> getAllResidentialComplexesWithoutApartments(Pageable pageable) {
        Page<ResidentialComplex> residentialComplexPage = residentialComplexRepository.findAllWithApartments(pageable);

        List<ResidentialComplexDTO> dtoList = residentialComplexPage.getContent()
                .stream()
                .map(ResidentialComplexMapper::maptoResidentialComplexDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                residentialComplexPage.getNumber(),
                residentialComplexPage.getSize(),
                residentialComplexPage.getTotalElements()
        );
    }

    @Override
    public PagedResponse<ResidentialComplexWithApartmentsDTO> getAllResidentialComplexesWithApartments(Pageable pageable) {
        Page<ResidentialComplex> residentialComplexPage = residentialComplexRepository.findAllWithApartments(pageable);

        List<ResidentialComplexWithApartmentsDTO> dtoList = residentialComplexPage.getContent()
                .stream()
                .map(ResidentialComplexMapper::mapToResidentialComplexWithApartmentsDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                residentialComplexPage.getNumber(),
                residentialComplexPage.getSize(),
                residentialComplexPage.getTotalElements()
        );
    }

    @Override
    public ResidentialComplexDTO updateResidentialComplex(Long id, ResidentialComplexDTO residentialComplexToUpdate) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id).orElseThrow(
                () -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id: " + id));
        residentialComplex.setName(residentialComplexToUpdate.getName());
        residentialComplex.setDeveloper(residentialComplexToUpdate.getDeveloper());
        residentialComplex.setPrice(residentialComplexToUpdate.getPrice());
        residentialComplex.setDistrict(residentialComplexToUpdate.getDistrict());
        residentialComplex.setAddress(residentialComplexToUpdate.getAddress());
        residentialComplex.setPromotionHeader(residentialComplexToUpdate.getPromotionHeader());
        residentialComplex.setPromotionText(residentialComplexToUpdate.getPromotionText());
        residentialComplex.setCompletedOrNot(residentialComplexToUpdate.getCompletedOrNot());
        residentialComplex.setNumberOfStoreys(residentialComplexToUpdate.getNumberOfStoreys());
        residentialComplex.setVideoUrl(residentialComplexToUpdate.getVideoUrl());
        residentialComplex.setDescription(residentialComplexToUpdate.getDescription());
        ResidentialComplex updatedResidentialComplex = residentialComplexRepository.save(residentialComplex);
        return ResidentialComplexMapper.maptoResidentialComplexDTO(updatedResidentialComplex);
    }

    @Override
    public void deleteResidentialComplex(Long id) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id).orElseThrow(
                () -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id: " + id)
        );
        residentialComplexRepository.deleteById(id);
    }
}

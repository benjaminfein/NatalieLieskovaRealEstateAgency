package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
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
                .collect(Collectors.groupingBy(
                        Apartment::getCountOfRooms,
                        Collectors.mapping(Apartment::getTotalArea, Collectors.toList())
                ));

        complex.setOneRoom(getRange(grouped.get(1)));
        complex.setTwoRoom(getRange(grouped.get(2)));
        complex.setThreeRoom(getRange(grouped.get(3)));
        complex.setFourRoom(getRange(grouped.get(4)));
        complex.setFiveRoom(getRange(grouped.get(5)));
    }

    private AreaRange getRange(List<Float> areas) {
        if (areas == null || areas.isEmpty()) return null;
        Float min = areas.stream().min(Float::compareTo).orElse(null);
        Float max = areas.stream().max(Float::compareTo).orElse(null);
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
    public PagedResponse<ResidentialComplexDTO> getAllResidentialComplexes(Pageable pageable) {
        Page<ResidentialComplex> residentialComplexPage = residentialComplexRepository.findAll(pageable);

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
    public ResidentialComplexDTO updateResidentialComplex(Long id, ResidentialComplexDTO residentialComplexToUpdate) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id).orElseThrow(
                () -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id: " + id));
        residentialComplex.setName(residentialComplexToUpdate.getName());
        residentialComplex.setDeveloper(residentialComplexToUpdate.getDeveloper());
        residentialComplex.setPrice(residentialComplexToUpdate.getPrice());
        residentialComplex.setDistrict(residentialComplexToUpdate.getDistrict());
        residentialComplex.setPromotionHeader(residentialComplexToUpdate.getPromotionHeader());
        residentialComplex.setPromotionText(residentialComplexToUpdate.getPromotionText());
        residentialComplex.setCompletedOrNot(residentialComplexToUpdate.getCompletedOrNot());
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

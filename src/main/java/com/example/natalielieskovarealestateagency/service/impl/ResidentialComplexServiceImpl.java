package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.exception.ResidentialComplexNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.ResidentialComplexMapper;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import com.example.natalielieskovarealestateagency.repository.ResidentialComplexRepository;
import com.example.natalielieskovarealestateagency.service.ResidentialComplexService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidentialComplexServiceImpl implements ResidentialComplexService {
    private ResidentialComplexRepository residentialComplexRepository;

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
    public List<ResidentialComplexDTO> getAllResidentialComplex() {
        List<ResidentialComplex> residentialComplexList = residentialComplexRepository.findAll();
        return residentialComplexList.stream().map(ResidentialComplexMapper::maptoResidentialComplexDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResidentialComplexDTO updateResidentialComplex(Long id, ResidentialComplexDTO residentialComplexToUpdate) {
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(id).orElseThrow(
                () -> new ResidentialComplexNotFoundException("Residential complex is not exist with given id: " + id));
        residentialComplex.setName(residentialComplexToUpdate.getName());
        residentialComplex.setDeveloper(residentialComplexToUpdate.getDeveloper());
        residentialComplex.setPrice(residentialComplexToUpdate.getPrice());
        residentialComplex.setDistrict(residentialComplexToUpdate.getDistrict());
        residentialComplex.setPromotion(residentialComplexToUpdate.getPromotion());
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

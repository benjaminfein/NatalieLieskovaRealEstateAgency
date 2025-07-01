package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;

import java.util.List;

public interface ResidentialComplexService {
    ResidentialComplexDTO createResidentialComplex(ResidentialComplexDTO residentialComplexDTO);

    ResidentialComplexDTO getResidentialComplexById(Long id);

    List<ResidentialComplexDTO> getAllResidentialComplex();

    ResidentialComplexDTO updateResidentialComplex(Long id, ResidentialComplexDTO residentialComplexDTO);

    void deleteResidentialComplex(Long id);
}

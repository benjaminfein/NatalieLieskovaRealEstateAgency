package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.ResidentialComplexDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResidentialComplexService {
    void updateAreaRanges(ResidentialComplex complex);

    ResidentialComplexDTO createResidentialComplex(ResidentialComplexDTO residentialComplexDTO);

    ResidentialComplexDTO getResidentialComplexById(Long id);

    PagedResponse<ResidentialComplexDTO> getAllResidentialComplexes(Pageable pageable);

    ResidentialComplexDTO updateResidentialComplex(Long id, ResidentialComplexDTO residentialComplexDTO);

    void deleteResidentialComplex(Long id);
}

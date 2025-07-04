package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.ResidentialComplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentialComplexRepository extends JpaRepository<ResidentialComplex, Long> {
    Page<ResidentialComplex> findAll(Pageable pageable);

    Optional<ResidentialComplex> findByName(String name);
}

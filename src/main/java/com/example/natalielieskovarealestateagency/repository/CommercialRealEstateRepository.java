package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.CommercialRealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommercialRealEstateRepository extends JpaRepository<CommercialRealEstate, Long> {
    Page<CommercialRealEstate> findAll(Pageable pageable);

    @Query("SELECT DISTINCT a.microDistrict FROM CommercialRealEstate a WHERE a.microDistrict IS NOT NULL")
    List<String> findAllDistinctMicroDistricts();

    @Query("SELECT DISTINCT a.countOfRooms FROM CommercialRealEstate a WHERE a.countOfRooms IS NOT NULL")
    List<Integer> findAllDistinctRoomCounts();

    Optional<CommercialRealEstate> findTopByAdminCreatorIdOrderByIdDesc(Long adminCreatorId);
}
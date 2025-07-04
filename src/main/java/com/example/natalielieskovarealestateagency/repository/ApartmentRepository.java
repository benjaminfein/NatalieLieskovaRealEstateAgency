package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    Page<Apartment> findAll(Pageable pageable);

    List<Apartment> findByResidentialComplexId(Long id);

    @Query("SELECT DISTINCT a.microDistrict FROM Apartment a WHERE a.microDistrict IS NOT NULL")
    List<String> findAllDistinctMicroDistricts();

    @Query("SELECT DISTINCT a.countOfRooms FROM Apartment a WHERE a.countOfRooms IS NOT NULL")
    List<Integer> findAllDistinctRoomCounts();
}

package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.HouseAndTownhouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseAndTownhouseRepository extends JpaRepository<HouseAndTownhouse, Long> {
    Page<HouseAndTownhouse> findAll(Pageable pageable);

    @Query("SELECT DISTINCT a.microDistrict FROM HouseAndTownhouse a WHERE a.microDistrict IS NOT NULL")
    List<String> findAllDistinctMicroDistricts();

    @Query("SELECT DISTINCT a.countOfRooms FROM HouseAndTownhouse a WHERE a.countOfRooms IS NOT NULL")
    List<Integer> findAllDistinctRoomCounts();

    Optional<HouseAndTownhouse> findTopByAdminCreatorIdOrderByIdDesc(Long adminCreatorId);
}

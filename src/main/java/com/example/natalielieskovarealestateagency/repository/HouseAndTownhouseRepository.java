package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.HouseAndTownhouse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseAndTownhouseRepository extends JpaRepository<HouseAndTownhouse, Long> {
    @Query("SELECT DISTINCT a.microDistrict FROM HouseAndTownhouse a WHERE a.microDistrict IS NOT NULL")
    List<String> findAllDistinctMicroDistricts();

    @Query("SELECT DISTINCT a.countOfRooms FROM HouseAndTownhouse a WHERE a.countOfRooms IS NOT NULL")
    List<Integer> findAllDistinctRoomCounts();
}

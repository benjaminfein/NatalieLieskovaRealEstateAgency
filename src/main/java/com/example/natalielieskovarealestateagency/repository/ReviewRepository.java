package com.example.natalielieskovarealestateagency.repository;

import com.example.natalielieskovarealestateagency.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}

package com.example.natalielieskovarealestateagency.service;

import com.example.natalielieskovarealestateagency.dto.ReviewDTO;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    PagedResponse<ReviewDTO> getAllReviews(Pageable pageable);

    ReviewDTO getReviewById(Long id);

    ReviewDTO createReview(ReviewDTO reviewDTO);

    void deleteReviewById(Long id);
}
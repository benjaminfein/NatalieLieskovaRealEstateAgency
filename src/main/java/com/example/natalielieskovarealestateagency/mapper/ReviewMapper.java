package com.example.natalielieskovarealestateagency.mapper;

import com.example.natalielieskovarealestateagency.dto.ReviewDTO;
import com.example.natalielieskovarealestateagency.model.Review;

public class ReviewMapper {
    public static ReviewDTO maptoReviewDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getReviewObjectUrl()
        );
    }

    public static Review maptoReview(ReviewDTO reviewDTO) {
        return new Review(
                reviewDTO.getId(),
                reviewDTO.getReviewObjectUrl()
        );
    }
}

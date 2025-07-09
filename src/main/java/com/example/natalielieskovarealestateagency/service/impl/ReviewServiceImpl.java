package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.dto.ReviewDTO;
import com.example.natalielieskovarealestateagency.exception.ApartmentNotFoundException;
import com.example.natalielieskovarealestateagency.exception.ReviewNotFoundException;
import com.example.natalielieskovarealestateagency.mapper.ReviewMapper;
import com.example.natalielieskovarealestateagency.model.PagedResponse;
import com.example.natalielieskovarealestateagency.model.Review;
import com.example.natalielieskovarealestateagency.repository.ReviewRepository;
import com.example.natalielieskovarealestateagency.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    @Override
    public PagedResponse<ReviewDTO> getAllReviews(Pageable pageable) {
        Page<Review> reviewPagePage = reviewRepository.findAll(pageable);

        List<ReviewDTO> dtoList = reviewPagePage.getContent()
                .stream()
                .map(ReviewMapper::maptoReviewDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                reviewPagePage.getNumber(),
                reviewPagePage.getSize(),
                reviewPagePage.getTotalElements()
        );
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        return ReviewMapper.maptoReviewDTO(review);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = ReviewMapper.maptoReview(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return ReviewMapper.maptoReviewDTO(savedReview);
    }

    @Override
    public void deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new ApartmentNotFoundException("Review is not exist with given id: " + id)
        );
        reviewRepository.deleteById(id);
    }
}

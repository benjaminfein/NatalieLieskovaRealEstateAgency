package com.example.natalielieskovarealestateagency.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    void uploadReviewFile(MultipartFile file, Long reviewId) throws IOException;

    void deleteReviewFile(Long reviewId, String fileName);
}

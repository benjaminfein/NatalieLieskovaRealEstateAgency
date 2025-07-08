package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/upload-review-file")
    public ResponseEntity<String> uploadReviewFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("reviewId") Long reviewId) {
        try {
            s3Service.uploadReviewFile(file, reviewId);
            return ResponseEntity.ok("Файлы успешно загружены и привязаны к отзыву.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-review-file/{reviewId}")
    public ResponseEntity<String> deleteReviewFile(@PathVariable Long reviewId,
                                              @RequestParam("fileNames") String fileName) {
        s3Service.deleteReviewFile(reviewId, fileName);
        return ResponseEntity.ok("Файлы удалены.");
    }
}

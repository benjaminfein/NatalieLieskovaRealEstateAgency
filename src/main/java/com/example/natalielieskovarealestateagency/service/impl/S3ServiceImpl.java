package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.model.Review;
import com.example.natalielieskovarealestateagency.repository.ReviewRepository;
import com.example.natalielieskovarealestateagency.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private final ReviewRepository reviewRepository;

    private S3Client createS3Client() {
        log.debug("[S3ServiceImpl] Создание S3-клиента с регионом: {}, bucket: {}", region, bucketName);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public void uploadReviewFile(MultipartFile file, Long reviewId) throws IOException {
        log.info("[S3ServiceImpl] Загрузка файла в S3 для отзыва с ID {}", reviewId);
        S3Client s3Client = createS3Client();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Отзыв с ID {} не найден при попытке загрузки файлов", reviewId);
                    return new RuntimeException("Отзыв не найден");
                });

        // Get an extension
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                : "";

        // Making name of file that seems like "reviews/1.jpg"
        String key = "reviews/" + reviewId + extension;
        log.debug("[S3ServiceImpl] Загрузка файла: {}, ключ: {}", file.getOriginalFilename(), key);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
        review.setReviewObjectUrl(fileUrl);
        log.debug("[S3ServiceImpl] Файл загружен: {}", fileUrl);

        reviewRepository.save(review);
        log.info("[S3ServiceImpl] Файлы успешно загружены и сохранены для квартиры {}", reviewId);
    }

    @Override
    public void deleteReviewFile(Long reviewId, String fileName) {
        log.info("[S3ServiceImpl] Удаление файла {} из S3 для отзыва с ID {}", fileName, reviewId);

        S3Client s3Client = createS3Client();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        String key = "reviews/" + fileName;

        log.debug("[S3ServiceImpl] Удаление объекта по ключу: {}", key);
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));

        review.setReviewObjectUrl(null);
        reviewRepository.save(review);
        log.info("[S3ServiceImpl] Файл удалён и запись обновлена для отзыва {}", reviewId);
    }
}

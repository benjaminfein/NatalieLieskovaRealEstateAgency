package com.example.natalielieskovarealestateagency.service.impl;

import com.example.natalielieskovarealestateagency.model.*;
import com.example.natalielieskovarealestateagency.repository.*;
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
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ApartmentRepository apartmentRepository;
    private final CommercialRealEstateRepository commercialRealEstateRepository;
    private final HouseAndTownhouseRepository houseAndTownhouseRepository;
    private final ResidentialComplexRepository residentialComplexRepository;

    private S3Client createS3Client() {
        log.debug("[S3ServiceImpl] Создание S3-клиента с регионом: {}, bucket: {}", region, bucketName);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //REVIEW METHODS:
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

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

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //APARTMENT METHODS:
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void uploadApartmentFiles(List<MultipartFile> files, Long apartmentId) throws IOException {
        log.info("[S3ServiceImpl] Загрузка {} файлов в S3 для квартиры с ID {}", files.size(), apartmentId);
        S3Client s3Client = createS3Client();
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Квартира с ID {} не найдена при попытке загрузки файлов", apartmentId);
                    return new RuntimeException("Квартира не найдена");
                });

        for (MultipartFile file : files) {
            String key = "apartments/" + apartmentId + "/" + file.getOriginalFilename();
            log.debug("[S3ServiceImpl] Загрузка файла: {}, ключ: {}", file.getOriginalFilename(), key);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
            apartment.getPhotoUrls().add(fileUrl);
            log.debug("[S3ServiceImpl] Файл загружен: {}", fileUrl);
        }

        apartmentRepository.save(apartment);
        log.info("[S3ServiceImpl] Файлы успешно загружены и сохранены для квартиры {}", apartmentId);
    }

    @Override
    public void deleteApartmentFiles(Long apartmentId, List<String> fileNames) {
        log.info("[S3ServiceImpl] Удаление {} файлов из S3 для квартиры с ID {}", fileNames.size(), apartmentId);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Квартира с ID {} не найдена при попытке удаления файлов", apartmentId);
                    return new RuntimeException("Квартира не найдена");
                });

        List<String> updatedUrls = new ArrayList<>(apartment.getPhotoUrls());

        for (String fileName : fileNames) {
            String key = "apartments/" + apartmentId + "/" + fileName;
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

            log.debug("[S3ServiceImpl] Удаление файла: {}", fileUrl);

            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));

            updatedUrls.removeIf(url -> url.equals(fileUrl));
        }

        apartment.setPhotoUrls(updatedUrls);
        apartmentRepository.save(apartment);
        log.info("[S3ServiceImpl] Файлы успешно удалены и изменения сохранены для квартиры {}", apartmentId);
    }

    @Override
    public List<String> listApartmentFiles(Long apartmentId) {
        log.info("[S3ServiceImpl] Получение списка файлов из S3 для квартиры с ID {}", apartmentId);
        S3Client s3Client = createS3Client();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("apartments/" + apartmentId + "/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> fileUrls = response.contents().stream()
                .map(s3Object -> String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3Object.key()))
                .collect(Collectors.toList());

        log.debug("[S3ServiceImpl] Найдено {} файлов для квартиры {}", fileUrls.size(), apartmentId);
        return fileUrls;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //COMMERCIAL METHODS:
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void uploadCommercialRealEstateFiles(List<MultipartFile> files, Long commercialRealEstateId) throws IOException {
        log.info("[S3ServiceImpl] Загрузка {} файлов в S3 для коммерции с ID {}", files.size(), commercialRealEstateId);
        S3Client s3Client = createS3Client();
        CommercialRealEstate commercialRealEstate = commercialRealEstateRepository.findById(commercialRealEstateId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Коммерция с ID {} не найдена при попытке загрузки файлов", commercialRealEstateId);
                    return new RuntimeException("Коммерция не найдена");
                });

        for (MultipartFile file : files) {
            String key = "commercialRealEstates/" + commercialRealEstateId + "/" + file.getOriginalFilename();
            log.debug("[S3ServiceImpl] Загрузка файла: {}, ключ: {}", file.getOriginalFilename(), key);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
            commercialRealEstate.getPhotoUrls().add(fileUrl);
            log.debug("[S3ServiceImpl] Файл загружен: {}", fileUrl);
        }

        commercialRealEstateRepository.save(commercialRealEstate);
        log.info("[S3ServiceImpl] Файлы успешно загружены и сохранены для коммерции {}", commercialRealEstateId);
    }

    @Override
    public void deleteCommercialRealEstateFiles(Long commercialRealEstateId, List<String> fileNames) {
        log.info("[S3ServiceImpl] Удаление {} файлов из S3 для коммерции с ID {}", fileNames.size(), commercialRealEstateId);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        CommercialRealEstate commercialRealEstate = commercialRealEstateRepository.findById(commercialRealEstateId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Коммерция с ID {} не найдена при попытке удаления файлов", commercialRealEstateId);
                    return new RuntimeException("Коммерция не найдена");
                });

        List<String> updatedUrls = new ArrayList<>(commercialRealEstate.getPhotoUrls());

        for (String fileName : fileNames) {
            String key = "commercialRealEstates/" + commercialRealEstateId + "/" + fileName;
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

            log.debug("[S3ServiceImpl] Удаление файла: {}", fileUrl);

            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));

            updatedUrls.removeIf(url -> url.equals(fileUrl));
        }

        commercialRealEstate.setPhotoUrls(updatedUrls);
        commercialRealEstateRepository.save(commercialRealEstate);
        log.info("[S3ServiceImpl] Файлы успешно удалены и изменения сохранены для коммерции {}", commercialRealEstateId);
    }

    @Override
    public List<String> listCommercialRealEstateFiles(Long commercialRealEstateId) {
        log.info("[S3ServiceImpl] Получение списка файлов из S3 для коммерции с ID {}", commercialRealEstateId);
        S3Client s3Client = createS3Client();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("commercialRealEstates/" + commercialRealEstateId + "/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> fileUrls = response.contents().stream()
                .map(s3Object -> String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3Object.key()))
                .collect(Collectors.toList());

        log.debug("[S3ServiceImpl] Найдено {} файлов для коммерции {}", fileUrls.size(), commercialRealEstateId);
        return fileUrls;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //HOUSE AND TOWNHOUSE METHODS:
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void uploadHouseAndTownhouseFiles(List<MultipartFile> files, Long houseAndTownhouseId) throws IOException {
        log.info("[S3ServiceImpl] Загрузка {} файлов в S3 для дома с ID {}", files.size(), houseAndTownhouseId);
        S3Client s3Client = createS3Client();
        HouseAndTownhouse houseAndTownhouse = houseAndTownhouseRepository.findById(houseAndTownhouseId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Дом с ID {} не найдена при попытке загрузки файлов", houseAndTownhouseId);
                    return new RuntimeException("Дом не найден");
                });

        for (MultipartFile file : files) {
            String key = "housesAndTownhouses/" + houseAndTownhouseId + "/" + file.getOriginalFilename();
            log.debug("[S3ServiceImpl] Загрузка файла: {}, ключ: {}", file.getOriginalFilename(), key);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
            houseAndTownhouse.getPhotoUrls().add(fileUrl);
            log.debug("[S3ServiceImpl] Файл загружен: {}", fileUrl);
        }

        houseAndTownhouseRepository.save(houseAndTownhouse);
        log.info("[S3ServiceImpl] Файлы успешно загружены и сохранены для дома {}", houseAndTownhouseId);
    }

    @Override
    public void deleteHouseAndTownhouseFiles(Long houseAndTownhouseId, List<String> fileNames) {
        log.info("[S3ServiceImpl] Удаление {} файлов из S3 для дома с ID {}", fileNames.size(), houseAndTownhouseId);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        HouseAndTownhouse houseAndTownhouse = houseAndTownhouseRepository.findById(houseAndTownhouseId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Дом с ID {} не найдена при попытке удаления файлов", houseAndTownhouseId);
                    return new RuntimeException("Дом не найден");
                });

        List<String> updatedUrls = new ArrayList<>(houseAndTownhouse.getPhotoUrls());

        for (String fileName : fileNames) {
            String key = "housesAndTownhouses/" + houseAndTownhouseId + "/" + fileName;
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

            log.debug("[S3ServiceImpl] Удаление файла: {}", fileUrl);

            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));

            updatedUrls.removeIf(url -> url.equals(fileUrl));
        }

        houseAndTownhouse.setPhotoUrls(updatedUrls);
        houseAndTownhouseRepository.save(houseAndTownhouse);
        log.info("[S3ServiceImpl] Файлы успешно удалены и изменения сохранены для дома {}", houseAndTownhouseId);
    }

    @Override
    public List<String> listHouseAndTownhouseFiles(Long houseAndTownhouseId) {
        log.info("[S3ServiceImpl] Получение списка файлов из S3 для дома с ID {}", houseAndTownhouseId);
        S3Client s3Client = createS3Client();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("housesAndTownhouses/" + houseAndTownhouseId + "/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> fileUrls = response.contents().stream()
                .map(s3Object -> String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3Object.key()))
                .collect(Collectors.toList());

        log.debug("[S3ServiceImpl] Найдено {} файлов для дома {}", fileUrls.size(), houseAndTownhouseId);
        return fileUrls;
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //HOUSE AND TOWNHOUSE METHODS:
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void uploadResidentialComplexFiles(List<MultipartFile> files, Long residentialComplexId) throws IOException {
        log.info("[S3ServiceImpl] Загрузка {} файлов в S3 для жилкомплекса с ID {}", files.size(), residentialComplexId);
        S3Client s3Client = createS3Client();
        ResidentialComplex residentialComplex = residentialComplexRepository.findById(residentialComplexId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Жилкомплекс с ID {} не найден при попытке загрузки файлов", residentialComplexId);
                    return new RuntimeException("Жилкомплекс не найден");
                });

        for (MultipartFile file : files) {
            String key = "residentialComplexes/" + residentialComplexId + "/" + file.getOriginalFilename();
            log.debug("[S3ServiceImpl] Загрузка файла: {}, ключ: {}", file.getOriginalFilename(), key);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
            residentialComplex.getPhotoUrls().add(fileUrl);
            log.debug("[S3ServiceImpl] Файл загружен: {}", fileUrl);
        }

        residentialComplexRepository.save(residentialComplex);
        log.info("[S3ServiceImpl] Файлы успешно загружены и сохранены для жилкомплекса {}", residentialComplex);
    }

    @Override
    public void deleteResidentialComplexFiles(Long residentialComplexId, List<String> fileNames) {
        log.info("[S3ServiceImpl] Удаление {} файлов из S3 для жилкомплекса с ID {}", fileNames.size(), residentialComplexId);
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        ResidentialComplex residentialComplex = residentialComplexRepository.findById(residentialComplexId)
                .orElseThrow(() -> {
                    log.error("[S3ServiceImpl] Жилкомплекс с ID {} не найдена при попытке удаления файлов", residentialComplexId);
                    return new RuntimeException("Жилкомплекс не найден");
                });

        List<String> updatedUrls = new ArrayList<>(residentialComplex.getPhotoUrls());

        for (String fileName : fileNames) {
            String key = "residentialComplexes/" + residentialComplexId + "/" + fileName;
            String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

            log.debug("[S3ServiceImpl] Удаление файла: {}", fileUrl);

            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));

            updatedUrls.removeIf(url -> url.equals(fileUrl));
        }

        residentialComplex.setPhotoUrls(updatedUrls);
        residentialComplexRepository.save(residentialComplex);
        log.info("[S3ServiceImpl] Файлы успешно удалены и изменения сохранены для жилкомплекса {}", residentialComplexId);
    }

    @Override
    public List<String> listResidentialComplexFiles(Long residentialComplexId) {
        log.info("[S3ServiceImpl] Получение списка файлов из S3 для жилкомплекса с ID {}", residentialComplexId);
        S3Client s3Client = createS3Client();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("residentialComplexes/" + residentialComplexId + "/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        List<String> fileUrls = response.contents().stream()
                .map(s3Object -> String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3Object.key()))
                .collect(Collectors.toList());

        log.debug("[S3ServiceImpl] Найдено {} файлов для жилкомплекса {}", fileUrls.size(), residentialComplexId);
        return fileUrls;
    }
}

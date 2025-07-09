package com.example.natalielieskovarealestateagency.controller;

import com.example.natalielieskovarealestateagency.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    //Review:-----------------------------------------------------------------------------------------------------------

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

    //Apartment:-----------------------------------------------------------------------------------------------------------

    @PostMapping("/upload-apartment-multiple-files")
    public ResponseEntity<String> uploadApartmentMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("apartmentId") Long apartmentId) {
        try {
            s3Service.uploadApartmentFiles(files, apartmentId);
            return ResponseEntity.ok("Файлы успешно загружены и привязаны к квартире.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    @GetMapping("/get-apartment-file-list/{apartmentId}")
    public ResponseEntity<List<String>> listApartmentFiles(@PathVariable Long apartmentId) {
        return ResponseEntity.ok(s3Service.listApartmentFiles(apartmentId));
    }

    @DeleteMapping("/delete-apartment-multiple-files/{apartmentId}")
    public ResponseEntity<String> deleteApartmentFiles(@PathVariable Long apartmentId,
                                              @RequestParam("fileNames") List<String> fileNames) {
        s3Service.deleteApartmentFiles(apartmentId, fileNames);
        return ResponseEntity.ok("Файлы удалены.");
    }

    //Commercial:-------------------------------------------------------------------------------------------------------

    @PostMapping("/upload-commercial-multiple-files")
    public ResponseEntity<String> uploadCommercialRealEstateMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("apartmentId") Long commercialRealEstateId) {
        try {
            s3Service.uploadCommercialRealEstateFiles(files, commercialRealEstateId);
            return ResponseEntity.ok("Файлы успешно загружены и привязаны к коммерции.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    @GetMapping("/get-commercial-file-list/{commercialRealEstateId}")
    public ResponseEntity<List<String>> listCommercialRealEstateFiles(@PathVariable Long commercialRealEstateId) {
        return ResponseEntity.ok(s3Service.listCommercialRealEstateFiles(commercialRealEstateId));
    }

    @DeleteMapping("/delete-commercial-multiple-files/{commercialRealEstateId}")
    public ResponseEntity<String> deleteCommercialRealEstateFiles(@PathVariable Long commercialRealEstateId,
                                                       @RequestParam("fileNames") List<String> fileNames) {
        s3Service.deleteCommercialRealEstateFiles(commercialRealEstateId, fileNames);
        return ResponseEntity.ok("Файлы удалены.");
    }

    //House and townhouse:----------------------------------------------------------------------------------------------

    @PostMapping("/upload-house-and-townhouse-multiple-files")
    public ResponseEntity<String> uploadHouseAndTownhouseMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("apartmentId") Long houseAndTownhouseId) {
        try {
            s3Service.uploadHouseAndTownhouseFiles(files, houseAndTownhouseId);
            return ResponseEntity.ok("Файлы успешно загружены и привязаны к дому.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    @GetMapping("/get-house-and-townhouse-file-list/{houseAndTownhouseId}")
    public ResponseEntity<List<String>> listHouseAndTownhouseFiles(@PathVariable Long houseAndTownhouseId) {
        return ResponseEntity.ok(s3Service.listHouseAndTownhouseFiles(houseAndTownhouseId));
    }

    @DeleteMapping("/delete-house-and-townhouse-multiple-files/{houseAndTownhouseId}")
    public ResponseEntity<String> deleteHouseAndTownhouseFiles(@PathVariable Long houseAndTownhouseId,
                                                                  @RequestParam("fileNames") List<String> fileNames) {
        s3Service.deleteHouseAndTownhouseFiles(houseAndTownhouseId, fileNames);
        return ResponseEntity.ok("Файлы удалены.");
    }

    //Residential complex:----------------------------------------------------------------------------------------------

    @PostMapping("/upload-residential-complex-multiple-files")
    public ResponseEntity<String> uploadResidentialComplexMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("apartmentId") Long residentialComplexId) {
        try {
            s3Service.uploadResidentialComplexFiles(files, residentialComplexId);
            return ResponseEntity.ok("Файлы успешно загружены и привязаны к жилкомплексу.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файлов: " + e.getMessage());
        }
    }

    @GetMapping("/get-residential-complex-file-list/{residentialComplexId}")
    public ResponseEntity<List<String>> listResidentialComplexFiles(@PathVariable Long residentialComplexId) {
        return ResponseEntity.ok(s3Service.listResidentialComplexFiles(residentialComplexId));
    }

    @DeleteMapping("/delete-residential-complex-multiple-files/{residentialComplexId}")
    public ResponseEntity<String> deleteResidentialComplexFiles(@PathVariable Long residentialComplexId,
                                                               @RequestParam("fileNames") List<String> fileNames) {
        s3Service.deleteResidentialComplexFiles(residentialComplexId, fileNames);
        return ResponseEntity.ok("Файлы удалены.");
    }
}

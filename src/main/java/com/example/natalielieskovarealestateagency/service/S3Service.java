package com.example.natalielieskovarealestateagency.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    //Review:-----------------------------------------------------------------------------------------------------------

    void uploadReviewFile(MultipartFile file, Long reviewId) throws IOException;

    void deleteReviewFile(Long reviewId, String fileName);

    //Apartment:--------------------------------------------------------------------------------------------------------

    void uploadApartmentFiles(List<MultipartFile> files, Long apartmentId) throws IOException;

    List<String> listApartmentFiles(Long apartmentId);

    void deleteApartmentFiles(Long apartmentId, List<String> fileNames);

    //Commercial:-------------------------------------------------------------------------------------------------------

    void uploadCommercialRealEstateFiles(List<MultipartFile> files, Long commercialRealEstateId) throws IOException;

    List<String> listCommercialRealEstateFiles(Long commercialRealEstateId);

    void deleteCommercialRealEstateFiles(Long commercialRealEstateId, List<String> fileNames);

    //House and townhouse:----------------------------------------------------------------------------------------------

    void uploadHouseAndTownhouseFiles(List<MultipartFile> files, Long houseAndTownhouseId) throws IOException;

    List<String> listHouseAndTownhouseFiles(Long houseAndTownhouseId);

    void deleteHouseAndTownhouseFiles(Long houseAndTownhouseId, List<String> fileNames);

    //Residential complex:----------------------------------------------------------------------------------------------

    void uploadResidentialComplexFiles(List<MultipartFile> files, Long residentialComplexId) throws IOException;

    List<String> listResidentialComplexFiles(Long residentialComplexId);

    void deleteResidentialComplexFiles(Long residentialComplexId, List<String> fileNames);
}

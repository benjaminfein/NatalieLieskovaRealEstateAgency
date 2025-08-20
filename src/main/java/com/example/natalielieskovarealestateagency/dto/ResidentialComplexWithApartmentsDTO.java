package com.example.natalielieskovarealestateagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentialComplexWithApartmentsDTO extends ResidentialComplexDTO {
    private List<ApartmentCardDTO> apartments;

    public ResidentialComplexWithApartmentsDTO(Long id, String name, String district, String address, String price, String developer,
                                               String promotionHeader, String promotionText, Integer numberOfStoreys, Boolean completedOrNot,
                                               AreaRangeDTO oneRoom, AreaRangeDTO twoRoom,
                                               AreaRangeDTO threeRoom, AreaRangeDTO fourRoom,
                                               AreaRangeDTO fiveRoom, String description, List<String> photoUrls,
                                               List<ApartmentCardDTO> apartments) {
        this.setId(id);
        this.setName(name);
        this.setDistrict(district);
        this.setAddress(address);
        this.setPrice(price);
        this.setDeveloper(developer);
        this.setPromotionHeader(promotionHeader);
        this.setPromotionText(promotionText);
        this.setNumberOfStoreys(numberOfStoreys);
        this.setCompletedOrNot(completedOrNot);
        this.setOneRoom(oneRoom);
        this.setTwoRoom(twoRoom);
        this.setThreeRoom(threeRoom);
        this.setFourRoom(fourRoom);
        this.setFiveRoom(fiveRoom);
        this.setDescription(description);
        this.setPhotoUrls(photoUrls);
        this.apartments = apartments;
    }
}

package com.example.natalielieskovarealestateagency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "house_and_townhouse", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class HouseAndTownhouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "microDistrict")
    private String microDistrict;
    @Column(name = "address")
    private String address;
    @Column(name = "price")
    private Integer price;
    @Column(name = "countOfRooms")
    private Integer countOfRooms;
    @Column(name = "nameOfResidentialComplex")
    private String nameOfResidentialComplex;
    @Column(name = "totalArea")
    private Float totalArea;
    @Column(name = "livingArea")
    private Float livingArea;
    @Column(name = "kitchenArea")
    private Float kitchenArea;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "numberOfStoreys")
    private Integer numberOfStoreys;
    @Column(name = "ceilingHeight")
    private Integer ceilingHeight;
    @Column(name = "propertyCondition")
    private String propertyCondition;
    @Column(name = "heating")
    private String heating;
    @Column(name = "propertyDescription")
    private String propertyDescription;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "house_and_townhouse_photos", joinColumns = @JoinColumn(name = "house_and_townhouse_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;
}

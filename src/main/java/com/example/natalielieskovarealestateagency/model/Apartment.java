package com.example.natalielieskovarealestateagency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "apartment", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "microDistrict")
    private String microDistrict;
    @Column(name = "address")
    private String address;
    @Column(name = "price")
    private Integer price;
    @Column(name = "countOfRooms")
    private Integer countOfRooms;
    @ManyToOne
    @JoinColumn(name = "residential_complex_id")  // внешний ключ
    private ResidentialComplex residentialComplex;
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
    private String ceilingHeight;
    @Column(name = "propertyCondition")
    private String propertyCondition;
    @Column(name = "heating")
    private String heating;
    @Column(name = "ownerPhoneNumber")
    private String ownerPhoneNumber;
    @Column(name = "propertyDescription")
    private String propertyDescription;
    @Column(name = "adminCreator")
    private String adminCreator;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "apartment_photos", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;

    public Apartment(Long id, String address, Integer price, Integer countOfRooms, Float totalArea,
                     Float livingArea, Float kitchenArea, String adminCreator, List<String> photoUrls) {
        this.id = id;
        this.address = address;
        this.price = price;
        this.countOfRooms = countOfRooms;
        this.totalArea = totalArea;
        this.livingArea = livingArea;
        this.kitchenArea = kitchenArea;
        this.adminCreator = adminCreator;
        this.photoUrls = photoUrls;
    }
}

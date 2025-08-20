package com.example.natalielieskovarealestateagency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "comercial_real_estate", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class CommercialRealEstate {
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
    @ManyToOne(optional = true)
    @JoinColumn(name = "residential_complex_id")
    private ResidentialComplex residentialComplex;
    @Column(name = "totalArea")
    private Float totalArea;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "numberOfStoreys")
    private Integer numberOfStoreys;
    @Column(name = "ceilingHeight")
    private String ceilingHeight;
    @Column(name = "propertyCondition")
    private String propertyCondition;
    @Column(name = "ownerPhoneNumber")
    private String ownerPhoneNumber;
    @Column(name = "propertyDescription")
    private String propertyDescription;
    @Column(name = "adminCreator")
    private String adminCreator;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "commercial_real_estate_photos", joinColumns = @JoinColumn(name = "commercial_real_estate_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;
}
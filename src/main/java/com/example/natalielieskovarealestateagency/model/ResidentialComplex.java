package com.example.natalielieskovarealestateagency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "residental_complex", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class ResidentialComplex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "district")
    private String district;
    @Column(name = "price")
    private String price;
    @Column(name = "developer")
    private String developer;
    @Column(name = "promotion")
    private String promotion;
    @Column(name = "completedOrNot")
    private Boolean completedOrNot;
    @OneToMany(mappedBy = "residentialComplex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "apartment_photos", joinColumns = @JoinColumn(name = "apartment_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;

    public ResidentialComplex(Long id, String name, String district, String price,
                              String developer, String promotion, Boolean completedOrNot) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.price = price;
        this.developer = developer;
        this.promotion = promotion;
        this.completedOrNot = completedOrNot;
        this.apartments = new ArrayList<>();
    }
}
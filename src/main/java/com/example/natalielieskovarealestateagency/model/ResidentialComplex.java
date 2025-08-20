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
    @Column(name = "address")
    private String address;
    @Column(name = "price")
    private String price;
    @Column(name = "developer")
    private String developer;
    @Column(name = "promotionHeader")
    private String promotionHeader;
    @Column(name = "promotionText")
    private String promotionText;
    @Column(name = "numberOfStoreys")
    private Integer numberOfStoreys;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "one_room_min")),
            @AttributeOverride(name = "max", column = @Column(name = "one_room_max"))
    })
    private AreaRange oneRoom;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "two_room_min")),
            @AttributeOverride(name = "max", column = @Column(name = "two_room_max"))
    })
    private AreaRange twoRoom;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "three_room_min")),
            @AttributeOverride(name = "max", column = @Column(name = "three_room_max"))
    })
    private AreaRange threeRoom;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "four_room_min")),
            @AttributeOverride(name = "max", column = @Column(name = "four_room_max"))
    })
    private AreaRange fourRoom;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "five_room_min")),
            @AttributeOverride(name = "max", column = @Column(name = "five_room_max"))
    })
    private AreaRange fiveRoom;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column(name = "completedOrNot")
    private Boolean completedOrNot;
    @OneToMany(mappedBy = "residentialComplex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "residential_complex_photos", joinColumns = @JoinColumn(name = "residential_complex_id"))
    @Column(name = "photo_url")
    private List<String> photoUrls;

    public ResidentialComplex(Long id, String name, String district, String address, String price,
                              String developer, String promotionHeader, String promotionText, Integer numberOfStoreys, AreaRange oneRoom,
                              AreaRange twoRoom, AreaRange threeRoom, AreaRange fourRoom, AreaRange fiveRoom,
                              String description, List<Apartment> apartments, Boolean completedOrNot) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.address = address;
        this.price = price;
        this.developer = developer;
        this.promotionHeader = promotionHeader;
        this.promotionText = promotionText;
        this.numberOfStoreys = numberOfStoreys;
        this.oneRoom = oneRoom;
        this.twoRoom = twoRoom;
        this.threeRoom = threeRoom;
        this.fourRoom = fourRoom;
        this.fiveRoom = fiveRoom;
        this.description = description;
        this.completedOrNot = completedOrNot;
        this.apartments = apartments;
    }

    public ResidentialComplex(Long id, String name, String district, String address, String price, String developer,
                              String promotionHeader, String promotionText, Integer numberOfStoreys, AreaRange oneRoom,
                              AreaRange twoRoom, AreaRange threeRoom, AreaRange fourRoom,
                              AreaRange fiveRoom, String description, Boolean completedOrNot) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.address = address;
        this.price = price;
        this.developer = developer;
        this.promotionHeader = promotionHeader;
        this.promotionText = promotionText;
        this.numberOfStoreys = numberOfStoreys;
        this.oneRoom = oneRoom;
        this.twoRoom = twoRoom;
        this.threeRoom = threeRoom;
        this.fourRoom = fourRoom;
        this.fiveRoom = fiveRoom;
        this.description = description;
        this.completedOrNot = completedOrNot;
    }
}
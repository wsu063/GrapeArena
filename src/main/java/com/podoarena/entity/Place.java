package com.podoarena.entity;

import com.podoarena.dto.PlaceFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "place")
@Getter
@Setter
public class Place {
    @Id
    @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;

    private String placeLocation;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String placeBatch;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceConcert> placeConcertList;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlaceImg placeImg;

    public void updatePlace(PlaceFormDto placeFormDto) {
        this.placeName = placeFormDto.getPlaceName();
        this.placeLocation = placeFormDto.getPlaceLocation();
        this.placeBatch = placeFormDto.getPlaceBatch();
    }
}

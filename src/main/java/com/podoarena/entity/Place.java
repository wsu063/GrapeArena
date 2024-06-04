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
@ToString
public class Place {
    @Id
    @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;

    private String placeLocation;

    private String placeBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_concert_id")
    private PlaceConcert placeConcert;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seatList;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlaceImg placeImg;

    public void updatePlace(PlaceFormDto placeFormDto) {
        this.placeName = placeFormDto.getPlaceName();
        this.placeLocation = placeFormDto.getPlaceLocation();
        this.placeBatch = placeFormDto.getPlaceBatch();
    }
}

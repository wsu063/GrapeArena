package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "place_concert")
@Getter
@Setter
@ToString
public class PlaceConcert {
    @Id
    @Column(name = "place_concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public static PlaceConcert createPlaceConcert(Place place, Concert concert) {
        PlaceConcert placeConcert = new PlaceConcert();
        placeConcert.setConcert(concert);
        placeConcert.setPlace(place);

        return placeConcert;
    }
}

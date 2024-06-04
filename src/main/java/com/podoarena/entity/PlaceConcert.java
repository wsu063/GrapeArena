package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_seat_id")
    private List<ReserveSeat> reserveSeatList;

    public static PlaceConcert createPlaceConcert(Place place, Concert concert) {
        PlaceConcert placeConcert = new PlaceConcert();
        placeConcert.setConcert(concert);
        placeConcert.setPlace(place);

        return placeConcert;
    }
}

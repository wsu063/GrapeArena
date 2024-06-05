package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "placeConcert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveSeat> reserveSeatList = new ArrayList<>();

    @OneToMany(mappedBy = "placeConcert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Date> dateList = new ArrayList<>();

    public PlaceConcert createPlaceConcert(Place place, Concert concert, List<Date> dateList) {
        PlaceConcert placeConcert = new PlaceConcert();

        for(Date date : dateList) {
            date.setPlaceConcert(placeConcert);
            placeConcert.dateList.add(date);
        }

        return placeConcert;
    }
}

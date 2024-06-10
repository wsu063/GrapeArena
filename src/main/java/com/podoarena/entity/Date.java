package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="date")
@Getter
@Setter
@ToString
public class Date {

    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_concert_id")
    private PlaceConcert placeConcert;

    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seatList;




}

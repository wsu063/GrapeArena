package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

    //2. 혹은 날짜(day)를 골랐을때, dateDay, dateTime이 존재하지 않으면 생성하는 방식으로 해야된다.


}

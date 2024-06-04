package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    private String dateDay;

    private String dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    //2. 혹은 날짜(day)를 골랐을때, dateDay, dateTime이 존재하지 않으면 생성하는 방식으로 해야된다.


}

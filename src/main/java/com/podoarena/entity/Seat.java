package com.podoarena.entity;

import com.podoarena.constant.SeatGrade;
import com.podoarena.constant.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@ToString
public class Seat{
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatName;

    private SeatStatus seatStatus;

    private String seatLocation;

    private SeatGrade seatGrade;

    private int seatPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveSeat> reserveSeatList;


    // 좌석 생성을 어떻게 할까요?
    // 1. 콘서트를 만들면
    // 2. 자동으로 미리 설정한 좌석을 만든다.
//    public Seat() {
//        if(this.seatGrade == SeatGrade.VIP)
//            this.seatPrice = 198000;
//        if(this.seatGrade == SeatGrade.R)
//            this.seatPrice = 154000;
//        if(this.seatGrade == SeatGrade.S)
//            this.seatPrice = 132000;
//    }
}

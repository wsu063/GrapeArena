package com.podoarena.entity;

import com.podoarena.constant.SeatGrade;
import com.podoarena.constant.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "concert_id")
    private Concert concert;


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

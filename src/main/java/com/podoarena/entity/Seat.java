package com.podoarena.entity;

import com.podoarena.constant.SeatGrade;
import com.podoarena.constant.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String seatLocation; // 현재 사용 안하는중

    private SeatGrade seatGrade;

    private int seatRow;

    private int seatLine;

    private int seatPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_concert_id")
    private PlaceConcert placeConcert;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveSeat> reserveSeatList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id")
    private Date date;

    public Seat() {};

    public Seat(PlaceConcert placeConcert, int row, int line, int seatStatus, Date date) {
        this.seatName = String.valueOf(row) + String.valueOf(line);
        this.seatStatus = SeatStatus.fromInt(seatStatus);
        //3열이내면 VIP, 5열이내면 R, 그외면 S
        this.seatGrade = row <= 3 ? SeatGrade.VIP : row <= 5 ? SeatGrade.R : SeatGrade.S;
        //좌석 등급에 따른 가격 책정
        this.seatPrice =
                seatGrade == SeatGrade.VIP ? 198000 : seatGrade == SeatGrade.R ? 154000 : 132000;
        this.placeConcert = placeConcert;
        this.seatLocation = placeConcert.getPlace().getPlaceLocation();
        this.date = date;
        this.seatLine = line;
        this.seatRow = row;
    }
}

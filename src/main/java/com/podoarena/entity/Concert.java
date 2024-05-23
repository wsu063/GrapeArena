package com.podoarena.entity;

import com.podoarena.constant.ConcertState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "concert")
@Getter
@Setter
@ToString
public class Concert extends BaseEntity {
    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String concertName; // 콘서트 이름
    
    private String concertPeriod; // 콘서트 기간.
    
    private String concertSinger; // 콘서트 가수
    
    private ConcertState concertState; // 콘서트 상태

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConcertImg> concertImgs;

//    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Seat> seatList;

}

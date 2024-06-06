package com.podoarena.entity;

import com.podoarena.constant.ConcertState;
import com.podoarena.dto.ConcertFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "concert")
@Getter
@Setter
public class Concert extends BaseEntity {
    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String concertName; // 콘서트 이름

    private String concertSinger; // 콘서트 가수
    
    private ConcertState concertState; // 콘서트 상태

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConcertImg> concertImgs;

    @OneToOne(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_concert_id")
    private PlaceConcert placeConcert;


    public void updateConcert(ConcertFormDto concertFormDto) {
        this.concertName = concertFormDto.getConcertName();
        this.concertSinger = concertFormDto.getConcertSinger();
        this.concertState = concertFormDto.getConcertState();
    }

}

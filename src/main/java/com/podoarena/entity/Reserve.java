package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reserve")
@Getter
@Setter
public class Reserve {
    @Id
    @Column(name = "reserve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveSeat> reserveSeats = new ArrayList<>();

    //추가
    public void addReserve(ReserveSeat reserveSeat) {
        this.reserveSeats.add(reserveSeat);
        reserveSeat.setReserve(this);
    }
    //생성
    public static Reserve createReserve(Member member) {
        Reserve reserve = new Reserve();
        reserve.setMember(member);

        return reserve;
    }
}

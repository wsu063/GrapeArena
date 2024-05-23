package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reserve_seat")
@Getter
@Setter
@ToString
public class ReserveSeat extends BaseEntity {
    @Id
    @Column(name = "reserve_seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //생성
    public static ReserveSeat crateReserveSeat(Seat seat, Member member, int seatCount) {
        ReserveSeat reserveSeat = new ReserveSeat();
        reserveSeat.setSeat(seat);
        reserveSeat.setReserve(member.getReserve());
        reserveSeat.setSeatCount(seatCount);

        return reserveSeat;
    }
}

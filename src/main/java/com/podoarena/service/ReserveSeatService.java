package com.podoarena.service;

import com.podoarena.constant.SeatStatus;
import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.Member;
import com.podoarena.entity.PlaceConcert;
import com.podoarena.entity.ReserveSeat;
import com.podoarena.entity.Seat;
import com.podoarena.repository.ReserveSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveSeatService {
    private final ReserveSeatRepository reserveSeatRepository;

    public Long saveReserveSeat(Seat seat, Member member, PlaceConcert placeConcert) throws Exception {
        ReserveSeat reserveSeat = ReserveSeat.crateReserveSeat(seat, member, placeConcert);
        reserveSeatRepository.save(reserveSeat);
        reserveSeat.setMember(member);
        member.getReserve().addReserve(reserveSeat);
        placeConcert.getReserveSeatList().add(reserveSeat);
        seat.setSeatStatus(SeatStatus.SELECTED);

        return reserveSeat.getId();
    }

    @Transactional(readOnly = true)
    public Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable) {
        Page<ReserveSeat> reserveSeatPage = reserveSeatRepository.getReserveSeatPage(reserveSeatSearchDto, pageable);
        return reserveSeatPage;
    }
}

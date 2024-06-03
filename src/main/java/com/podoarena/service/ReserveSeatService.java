package com.podoarena.service;

import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.ReserveSeat;
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

    @Transactional(readOnly = true)
    public Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable) {
        Page<ReserveSeat> reserveSeatPage = reserveSeatRepository.getReserveSeatPage(reserveSeatSearchDto, pageable);
        return reserveSeatPage;
    }
}

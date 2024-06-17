package com.podoarena.repository;

import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.ReserveSeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReserveSeatRepositoryCustom {

    Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable);
}

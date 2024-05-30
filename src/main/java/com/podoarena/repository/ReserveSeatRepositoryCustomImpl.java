package com.podoarena.repository;

import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.ReserveSeat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReserveSeatRepositoryCustomImpl implements ReserveSeatRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable) {
//        List<ReserveSeat> content = queryFactory
//                .selectFrom();


        return null;
    }
}

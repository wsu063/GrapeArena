package com.podoarena.repository;

import com.podoarena.entity.Concert;
import com.podoarena.entity.QConcert;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class ConcertRepositoryCustomImpl implements  ConcertRepositoryCustom{

    private JPAQueryFactory queryFactory;
    @Override
    public List<Concert> getConcertList() {
        List<Concert> concerts = queryFactory
                .selectFrom(QConcert.concert)
                .orderBy(QConcert.concert.regDate.desc())
                .fetch();

        return concerts;
    }
}

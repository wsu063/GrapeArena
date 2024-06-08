package com.podoarena.repository;

import com.podoarena.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DateRepositoryCustomImpl implements DateRepositoryCustom {
    public DateRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    private JPAQueryFactory queryFactory;
    @Override
    public List<Date> getDateByConcertIdOrderByDateAsc(Long concertId) {
        List<Date> content = queryFactory
                .selectFrom(QDate.date)
                .join(QPlaceConcert.placeConcert).on(QDate.date.placeConcert.eq(QPlaceConcert.placeConcert))
                .join(QConcert.concert).on(QPlaceConcert.placeConcert.concert.eq(QConcert.concert))
                .where(QConcert.concert.id.eq(concertId))
                .orderBy(QDate.date.dateTime.asc())
                .fetch();
        return content;
    }
}

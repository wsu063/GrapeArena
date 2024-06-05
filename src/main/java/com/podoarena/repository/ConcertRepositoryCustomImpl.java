package com.podoarena.repository;

import com.podoarena.constant.ConcertState;
import com.podoarena.dto.ConcertSearchDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.QConcert;
import com.podoarena.entity.QPlace;
import com.podoarena.entity.QPlaceConcert;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertRepositoryCustomImpl implements  ConcertRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ConcertRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); // 현재 날짜, 시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QConcert.concert.placeConcert.dateList.get(0).dateTime.after(dateTime); // 몇일전 이후부터
    }

    private BooleanExpression searchTypeStatusEq(ConcertState concertState) {
        return concertState == null ? null : QConcert.concert.concertState.eq(concertState);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("concert", searchBy)) { // 콘서트 검색시
            return QConcert.concert.concertName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("place", searchBy)) { // 공연장 검색시
            return QConcert.concert.placeConcert.place.placeName.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public List<Concert> getConcertList() {
        List<Concert> concerts = queryFactory
                .selectFrom(QConcert.concert)
                .orderBy(QConcert.concert.regDate.desc())
                .fetch();

        return concerts;
    }

    @Override
    public Page<Concert> getAdminConcertPage(ConcertSearchDto concertSearchDto, Pageable pageable) {
        List<Concert> content = queryFactory
                .selectFrom(QConcert.concert)
                .where(regDtsAfter(concertSearchDto.getSearchDateType()),
                        searchTypeStatusEq(concertSearchDto.getSearchTypeStatus()),
                        searchByLike(concertSearchDto.getSearchBy(), concertSearchDto.getSearchQuery()))
                .orderBy(QConcert.concert.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QConcert.concert)
                .where(regDtsAfter(concertSearchDto.getSearchDateType()),
                        searchTypeStatusEq(concertSearchDto.getSearchTypeStatus()),
                        searchByLike(concertSearchDto.getSearchBy(), concertSearchDto.getSearchQuery()))
                .fetchOne();


        return new PageImpl<>(content, pageable, total);
    }
}

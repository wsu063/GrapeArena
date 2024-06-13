package com.podoarena.repository;

import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.QReserveSeat;
import com.podoarena.entity.ReserveSeat;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ReserveSeatRepositoryCustomImpl implements ReserveSeatRepositoryCustom {

    public ReserveSeatRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    private JPAQueryFactory queryFactory;

    private BooleanExpression searchByLike(String searchQuery) {
        return QReserveSeat.reserveSeat.member.email.like("%" + searchQuery + "%");
    }

    @Override
    public Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable) {
        //관리자 페이지에서 예매한 좌석을 볼 수 있다.
        //회원 아이디(이메일)로 검색할 수 있다.
        List<ReserveSeat> content = queryFactory
                .selectFrom(QReserveSeat.reserveSeat)
                .where(searchByLike(reserveSeatSearchDto.getSearchQuery()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QReserveSeat.reserveSeat)
                .where(searchByLike(reserveSeatSearchDto.getSearchQuery()))
                .fetchOne();


        return new PageImpl<>(content, pageable, total);
    }
}

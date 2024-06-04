package com.podoarena.repository;

import com.podoarena.entity.Place;
import com.podoarena.entity.QPlace;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Page;

import java.util.List;

public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {
    public PlaceRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    private JPAQueryFactory queryFactory;

    @Override
    public List<Place> getPlaceAll() {
        List<Place> content = queryFactory
                .selectFrom(QPlace.place)
                .orderBy(QPlace.place.id.desc())
                .fetch();

        return content;
    }
}

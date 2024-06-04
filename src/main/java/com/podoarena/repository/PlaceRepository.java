package com.podoarena.repository;

import com.podoarena.entity.Cart;
import com.podoarena.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlaceRepository extends JpaRepository<Place, Long>,
    PlaceRepositoryCustom, QuerydslPredicateExecutor<Place> {


}

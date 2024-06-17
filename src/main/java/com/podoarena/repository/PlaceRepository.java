package com.podoarena.repository;

import com.podoarena.entity.Cart;
import com.podoarena.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByOrderByIdDesc();

}

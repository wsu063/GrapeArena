package com.podoarena.repository;

import com.podoarena.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long>,
        QuerydslPredicateExecutor<Concert>, ConcertRepositoryCustom {

}

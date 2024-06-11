package com.podoarena.repository;

import com.podoarena.entity.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface DateRepository extends JpaRepository<Date, Long>{
    List<Date> findByPlaceConcertIdOrderByIdAsc(Long placeConcertId);
}

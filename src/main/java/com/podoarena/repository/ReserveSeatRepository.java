package com.podoarena.repository;

import com.podoarena.entity.ReserveSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReserveSeatRepository extends JpaRepository<ReserveSeat, Long>,
        QuerydslPredicateExecutor<ReserveSeat>, ReserveSeatRepositoryCustom {

}

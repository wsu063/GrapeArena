package com.podoarena.repository;

import com.podoarena.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GoodsRepository extends JpaRepository<Goods, Long> ,
    QuerydslPredicateExecutor<Goods>, GoodsRepositoryCustom {

    }

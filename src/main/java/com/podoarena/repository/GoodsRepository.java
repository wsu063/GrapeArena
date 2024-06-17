package com.podoarena.repository;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> ,
    QuerydslPredicateExecutor<Goods>, GoodsRepositoryCustom {

//    List<GoodsCartDto> findByCartId(Long id);
}

package com.podoarena.repository;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Cart;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsCartRepository extends JpaRepository<GoodsCart, Long>,
        QuerydslPredicateExecutor<GoodsCart>, GoodsCartRepositoryCustom {
    GoodsCart findByGoodsId(Long id);

    List<GoodsCart> findByMember(Member member);

}

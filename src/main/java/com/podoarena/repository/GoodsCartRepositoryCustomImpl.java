package com.podoarena.repository;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.QGoodsCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class GoodsCartRepositoryCustomImpl implements GoodsCartRepositoryCustom{
    public GoodsCartRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    private JPAQueryFactory queryFactory;


    @Override
    public List<GoodsCart> findGoodsCartList(Long cartId) {
        List<GoodsCart> goodsCartList = queryFactory
                .selectFrom(QGoodsCart.goodsCart)
                .where(QGoodsCart.goodsCart.id.eq(cartId))
                .fetch();

        return goodsCartList;
    }
}

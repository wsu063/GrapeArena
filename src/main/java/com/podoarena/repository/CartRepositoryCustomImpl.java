package com.podoarena.repository;

import com.podoarena.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CartRepositoryCustomImpl implements CartRepositoryCustom {
    public CartRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    private JPAQueryFactory queryFactory;
    @Override
    public List<Cart> getCartByGoodsId(List<Long> goodsIdList) {
        List<Cart> content = queryFactory
                .selectFrom(QCart.cart)
                .join(QGoodsCart.goodsCart).on(QCart.cart.goodsCarts.contains(QGoodsCart.goodsCart))
                .join(QGoods.goods).on(QGoodsCart.goodsCart.goods.eq(QGoods.goods))
                .where(QGoods.goods.id.in(goodsIdList))
                .fetch();
        return content;
    }
}

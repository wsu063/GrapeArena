package com.podoarena.repository;

import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.QGoods;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{
    private JPAQueryFactory queryFactory;

    private BooleanExpression searchQuery(String searchQuery) {
        return QGoods.goods.goodsName.like("%" + searchQuery + "%");
    }


    @Override
    public List<Goods> getGoodsList(GoodsSearchDto goodsSearchDto) {
        List<Goods> goods = queryFactory
                .selectFrom(QGoods.goods)
                .orderBy(QGoods.goods.regDate.desc())
                .fetch();
        return goods;
    }
}

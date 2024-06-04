package com.podoarena.repository;

import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.QGoods;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{
    private JPAQueryFactory queryFactory;

    private  BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)  ? null : QGoods.goods.goodsName.like("%"+searchQuery+"%");
    }

    private BooleanExpression searchQuery(String searchQuery) {
        return QGoods.goods.goodsName.like("%" + searchQuery + "%");
    }


    @Override
    public Page<Goods> getGoodsList(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        List<Goods> goods = queryFactory
                .selectFrom(QGoods.goods)
                .where(itemNmLike(goodsSearchDto.getSearchQuery()))
                .orderBy(QGoods.goods.sellStatus.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QGoods.goods)
                .where(itemNmLike(goodsSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(goods, pageable, total);
    }
}

package com.podoarena.repository;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.dto.QMainGoodsDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.QGoods;
import com.podoarena.entity.QGoodsImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public GoodsRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private  BooleanExpression goodsNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)  ? null : QGoods.goods.goodsName.like("%"+searchQuery+"%");
    }

    private BooleanExpression searchQuery(String searchQuery) {
        return QGoods.goods.goodsName.like("%" + searchQuery + "%");
    }


    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        List<Goods> content = queryFactory
                .selectFrom(QGoods.goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(QGoods.goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QGoods.goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainGoodsDto> getMainGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        QGoods goods = QGoods.goods;
        QGoodsImg goodsImg = QGoodsImg.goodsImg;

        List<MainGoodsDto> content = queryFactory
                .select(
                        new QMainGoodsDto(
                                goods.id,
                                goods.goodsName,
                                goods.goodsPrice,
                                goods.goodsStock,
                                goods.goodsMaxAmount,
                                goodsImg.imgUrl)
                )
                .from(goodsImg)
                .join(goodsImg.goods, goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(goodsImg)
                .join(goodsImg.goods, goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(goods.id.desc())
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}

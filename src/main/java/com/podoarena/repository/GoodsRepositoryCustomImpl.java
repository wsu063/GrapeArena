package com.podoarena.repository;

import com.podoarena.constant.ConcertState;
import com.podoarena.constant.RepImgYn;
import com.podoarena.constant.SellStatus;
import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.dto.QMainGoodsDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.QConcert;
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

import java.time.LocalDateTime;
import java.util.List;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public GoodsRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchQuery(String searchQuery) {
        return QGoods.goods.goodsName.like("%" + searchQuery + "%");
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); // 현재 날짜, 시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QGoods.goods.regDate.after(dateTime); // 몇일전 이후부터
    }

    private BooleanExpression searchTypeStatusEq(SellStatus sellStatus) {
        return sellStatus == null ? null : QGoods.goods.sellStatus.eq(sellStatus);
    }

    //굿즈 구매내역 리스트 가져오기
    @Override
    public List<Goods> getGoodsList() {
        List<Goods> goods = queryFactory
                .selectFrom(QGoods.goods)
                .orderBy(QGoods.goods.id.desc())
                .fetch();

        return goods;

    }

    private  BooleanExpression goodsNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)  ? null : QGoods.goods.goodsName.like("%"+searchQuery+"%");
    }

    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        List<Goods> content = queryFactory
                .selectFrom(QGoods.goods)
                .where(regDtsAfter(goodsSearchDto.getSearchDateType()),
                        searchTypeStatusEq(goodsSearchDto.getSearchSellStatus()),
                        goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(QGoods.goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QGoods.goods)
                .where(regDtsAfter(goodsSearchDto.getSearchDateType()),
                        searchTypeStatusEq(goodsSearchDto.getSearchSellStatus()),
                        goodsNameLike(goodsSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Goods> getMainGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        QGoods goods = QGoods.goods;
        QGoodsImg goodsImg = QGoodsImg.goodsImg;

        List<Goods> content = queryFactory
                .selectFrom(QGoods.goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(goods.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QGoods.goods)
                .where(goodsNameLike(goodsSearchDto.getSearchQuery()))
                .orderBy(goods.id.desc())
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


}

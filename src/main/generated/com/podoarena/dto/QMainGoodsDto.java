package com.podoarena.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.podoarena.dto.QMainGoodsDto is a Querydsl Projection type for MainGoodsDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainGoodsDto extends ConstructorExpression<MainGoodsDto> {

    private static final long serialVersionUID = -756721873L;

    public QMainGoodsDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> goodsName, com.querydsl.core.types.Expression<Integer> goodsPrice, com.querydsl.core.types.Expression<Integer> goodsStock, com.querydsl.core.types.Expression<Integer> goodsMaxAccount, com.querydsl.core.types.Expression<String> imgUrl) {
        super(MainGoodsDto.class, new Class<?>[]{long.class, String.class, int.class, int.class, int.class, String.class}, id, goodsName, goodsPrice, goodsStock, goodsMaxAccount, imgUrl);
    }

}


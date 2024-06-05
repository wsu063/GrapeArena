package com.podoarena.dto;

import com.podoarena.entity.GoodsImg;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainGoodsDto {
    private Long id;
    private String goodsName;
    private int goodsPrice;
    private int goodsStock;
    private int goodsMaxAccount;
    private List<GoodsImg> goodsImgs;

    @QueryProjection
    public MainGoodsDto(Long id, String goodsName, int goodsPrice, int goodsStock,
                        int goodsMaxAccount) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
        this.goodsMaxAccount = goodsMaxAccount;
    }
}

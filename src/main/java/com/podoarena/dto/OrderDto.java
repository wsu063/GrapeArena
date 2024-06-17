package com.podoarena.dto;

import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsCart;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class OrderDto {

    private List<Long> goodsCartIds  = new ArrayList<>(); // 장바구니에서 구매할때 필요

    private List<Long> goodsIds = new ArrayList<>(); // 바로구매할때 필요

    private List<Long> goodsCounts = new ArrayList<>(); // 바로구매할때 필요


}

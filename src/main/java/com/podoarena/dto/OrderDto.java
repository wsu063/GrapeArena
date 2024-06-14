package com.podoarena.dto;

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

    private List<Long> goodsCartIds;

    private List<GoodsCart> goodsCartList = new ArrayList<>();

}

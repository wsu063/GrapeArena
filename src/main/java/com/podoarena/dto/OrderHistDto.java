package com.podoarena.dto;

import com.podoarena.constant.OrderStatus;
import com.podoarena.entity.OrderGoods;
import com.podoarena.entity.Orders;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    // entity -> Dto로 변환
    public OrderHistDto(Orders orders) {
        this.orderId = orders.getId();

    }

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private List<GoodsCartDto> goodsCartDtoList = new ArrayList<>();
}

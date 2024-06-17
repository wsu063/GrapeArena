package com.podoarena.dto;

import com.podoarena.constant.OrderStatus;
import com.podoarena.entity.Orders;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    // entity -> Dto로 변환
    public OrderHistDto(Orders orders) {
        this.orderId = orders.getId();
        this.orderDate = orders.getOrderDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = orders.getOrderStatus();
    }

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private List<GoodsCartDto> goodsCartDtoList = new ArrayList<>(); //주문상태리스트

    //goodsCartDto 객체를 주문 상품 리스트에 추가
    public void addGoodsCartDto(GoodsCartDto goodsCartDto)  {this.goodsCartDtoList.add(goodsCartDto);}

}

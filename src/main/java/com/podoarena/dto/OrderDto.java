package com.podoarena.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long orderId;
    private List<OrderDto> orderDtoList;


}

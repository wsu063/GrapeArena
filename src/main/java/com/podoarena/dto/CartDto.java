package com.podoarena.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long goodsId;

    private int goodsCount;
}

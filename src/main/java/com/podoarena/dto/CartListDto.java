package com.podoarena.dto;

import com.podoarena.entity.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class CartListDto {
    public CartListDto(Cart cart) {this.cartId = cart.getId();}

    private Long cartId;

    private List<GoodsCartDto> goodsCartDtoList = new ArrayList<>();

    public void addGoodsCartDto(GoodsCartDto goodsCartDto) {this.goodsCartDtoList.add(goodsCartDto);}
}

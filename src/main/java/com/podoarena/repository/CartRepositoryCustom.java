package com.podoarena.repository;

import com.podoarena.entity.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    List<Cart> getCartByGoodsId(List<Long>goodsIdList);
}

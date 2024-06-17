package com.podoarena.repository;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.GoodsCart;

import java.util.List;

public interface GoodsCartRepositoryCustom {
    List<GoodsCart> findGoodsCartList (Long cartId);
}

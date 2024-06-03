package com.podoarena.repository;

import com.podoarena.entity.Cart;
import com.podoarena.entity.GoodsCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsCartRepository extends JpaRepository<GoodsCart, Long> {
    GoodsCart findByGoodsId(Long id);
}

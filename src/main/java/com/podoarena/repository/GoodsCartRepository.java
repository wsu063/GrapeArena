package com.podoarena.repository;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Cart;
import com.podoarena.entity.GoodsCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsCartRepository extends JpaRepository<GoodsCart, Long> {
    GoodsCart findByGoodsId(Long id);

    List<GoodsCartDto> findGoodsCartDtoList (@Param("goodscartId") Long goodscartId);
}

package com.podoarena.repository;

import com.podoarena.entity.Cart;
import com.podoarena.entity.GoodsCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
     Cart findByMemberId(Long memberId);


}

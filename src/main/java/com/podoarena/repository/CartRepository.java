package com.podoarena.repository;

import com.podoarena.entity.Cart;
import com.podoarena.entity.GoodsCart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
     Cart findByMemberId(Long memberId);

     @Query("select c from Cart c where c.member.email = :email")
     List<Cart> findCart(@Param("email") String email);

     @Query("select count(c) from Cart  c where c.member.email = :email")
     Long countCart(@Param("email") String email);

     @Query("select gc.goods.id from Cart c join c.goodsCarts gc where c.member.id = :memberId")
     List<Long> findGoodsIdsByMemberId(@Param("memberId") Long memberId);

}

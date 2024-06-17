package com.podoarena.repository;

import com.podoarena.entity.Date;
import com.podoarena.entity.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}

package com.podoarena.repository;

import com.podoarena.constant.RepImgYn;
import com.podoarena.entity.GoodsImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsImgRepository extends JpaRepository<GoodsImg, Long> {
    List<GoodsImg> findByGoodsIdOrderByIdAsc(Long goodsId);
    GoodsImg findByGoodsIdAndRepImgYn(Long goodsId, RepImgYn repImgYn);
}

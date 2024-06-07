package com.podoarena.repository;

import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepositoryCustom {

    //굿즈 구매내역
    List<Goods> getGoodsList();

    Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);

    Page<Goods> getMainGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);
}

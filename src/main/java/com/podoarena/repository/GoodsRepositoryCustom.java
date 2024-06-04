package com.podoarena.repository;

import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepositoryCustom {
    Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);

    Page<MainGoodsDto> getMainGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable);
}

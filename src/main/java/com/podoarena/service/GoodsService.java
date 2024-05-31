package com.podoarena.service;

import com.podoarena.dto.GoodsFormDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsImg;
import com.podoarena.repository.GoodsImgRepository;
import com.podoarena.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImgRepository goodsImgRepository;
    private final GoodsImgService goodsImgService;


    // 굿즈 등록하기
    public Long saveGoods(GoodsFormDto goodsFormDto,
                         List<MultipartFile> goodsImgFileList) throws Exception {
        Goods goods = goodsFormDto.createGoods();
        goodsRepository.save(goods);

        for (int i = 0; i < goodsImgFileList.size(); i++) {
            GoodsImg goodsImg = new GoodsImg();
            goodsImg.setGoods(goods);

            if (i == 0) {
                goodsImg.setRepImgYn("Y");
            } else {
                goodsImg.setRepImgYn("N");
            }

            goodsImgService.saveGoodsImg(goodsImg, goodsImgFileList.get(i));
        }

        return goods.getId();
    }
}

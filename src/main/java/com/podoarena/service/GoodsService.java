package com.podoarena.service;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.GoodsFormDto;
import com.podoarena.dto.GoodsImgDto;
import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsImg;
import com.podoarena.repository.GoodsImgRepository;
import com.podoarena.repository.GoodsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
                goodsImg.setRepImgYn(RepImgYn.Y);
            } else {
                goodsImg.setRepImgYn(RepImgYn.N);
            }

            goodsImgService.saveGoodsImg(goodsImg, goodsImgFileList.get(i));
        }

        return goods.getId();
    }

    //상품리스트 이미지 가져오기
    @Transactional(readOnly = true)
    public GoodsFormDto getGoodsDtl(Long goodsId) {
        List<GoodsImg> goodsImgList = goodsImgRepository.findByGoodsIdOrderByIdAsc(goodsId);

        List<GoodsImgDto> goodsImgDtoList = new ArrayList<>();
        for (GoodsImg goodsImg : goodsImgList) {
            GoodsImgDto goodsImgDto = GoodsImgDto.of(goodsImg);
            goodsImgDtoList.add(goodsImgDto);
        }

        Goods goods = goodsRepository.findById(goodsId).orElseThrow(EntityNotFoundException::new);

        GoodsFormDto goodsFormDto = GoodsFormDto.of(goods);
        goodsFormDto.setGoodsImgDtoList(goodsImgDtoList);

        return goodsFormDto;
    }

    //굿즈 삭제하기
    public void deleteGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(EntityNotFoundException::new);

        goodsRepository.delete(goods);
    }

    @Transactional(readOnly = true)
    public Page<Goods> getAdminGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable){
        Page<Goods> goodsPage = goodsRepository.getAdminGoodsPage(goodsSearchDto, pageable);
        return goodsPage;
    }

    //굿즈어드민 페이징
    public Page<MainGoodsDto> getMainGoodsList(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        Page<MainGoodsDto> mainGoodsDtoPage = goodsRepository.getMainGoodsPage(goodsSearchDto, pageable);
        return mainGoodsDtoPage;
    }



}

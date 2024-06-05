package com.podoarena.service;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.*;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsImg;
import com.podoarena.entity.Place;
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

    //3. 굿즈 수정(관리자)
    public Long updateGoods(GoodsFormDto goodsFormDto,
                            List<MultipartFile> goodsImgFileList) throws Exception {

        Goods goods = goodsRepository.findById(goodsFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        goods.updateGoods(goodsFormDto);

        List<Long> goodsImgIds = goodsFormDto.getGoodsImgIds(); // 상품 이미지 아이디 리스트 조회

        // 5개의 이미지 파일을 업로드 했으므로 아래처럼 for문을 이용해 하나씩 이미지 업데이트를 진행
        for (int i = 0; i < goodsImgFileList.size(); i++) {
            goodsImgService.updateGoodsImg(goodsImgIds.get(i), goodsImgFileList.get(i) );
        }

        return goods.getId();
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

    //굿즈구매내역 가져오기
    public List<Goods> getGoodsList() {
        List<Goods> goods = goodsRepository.getGoodsList();
        return goods;
    }

    //굿즈리스트 페이징
    public Page<Goods> getMainGoodsPage(GoodsSearchDto goodsSearchDto, Pageable pageable) {
        Page<Goods> mainGoodsPage = goodsRepository.getMainGoodsPage(goodsSearchDto, pageable);
        return mainGoodsPage;
    }


}

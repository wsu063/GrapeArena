package com.podoarena.service;

import com.podoarena.entity.GoodsImg;
import com.podoarena.repository.GoodsImgRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsImgService {

    @Value("${goodsImgLocation}") // C:/shop/item
    private String goodsImgLocation;

    private final GoodsImgRepository goodsImgRepository;
    private final FileService fileService;

    public void saveGoodsImg(GoodsImg goodsImg, MultipartFile goodsImgFile) throws Exception{
        String oriImgName = goodsImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(goodsImgLocation,
                    oriImgName, goodsImgFile.getBytes());

            imgUrl = "/images/goods/" + imgName;
        }

        goodsImg.updateGoodsImg(oriImgName, imgName, imgUrl);
        goodsImgRepository.save(goodsImg);
    }

    // 이미지 수정
    public void updateGoodsImg(Long goodsImgId, MultipartFile goodsImgFile) throws Exception{
        if(!goodsImgFile.isEmpty()){ // 첨부한 이미지 파일이 있으면

            // 1. 서버에 있는 이미지를 가지고 와서 수정해준다.
            GoodsImg savedGoodsImg = goodsImgRepository.findById(goodsImgId)
                    .orElseThrow(EntityExistsException::new);

            // 기존 이미지 파일을 c:/shop/item 폴더에서 삭제
            if(!StringUtils.isEmpty(savedGoodsImg.getImgName())) {
                fileService.deleteFile(goodsImgLocation + "/" + savedGoodsImg.getImgName());
            }

            // 수정된 이미지 파일을 c:/shop/item 폴더에 업로드
            String oriImgName = goodsImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(goodsImgLocation, oriImgName,
                    goodsImgFile.getBytes());
            String imgUrl = "/images/goods/" + imgName;

            // 2. item_img 테이블에 저장된 데이터를 수정해준다.
            // update( JPA가 자동감지 )
            savedGoodsImg.updateGoodsImg(oriImgName, imgName, imgUrl);


        }


    }
}

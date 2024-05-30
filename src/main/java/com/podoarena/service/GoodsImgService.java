package com.podoarena.service;

import com.podoarena.entity.GoodsImg;
import com.podoarena.repository.GoodsImgRepository;
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
}

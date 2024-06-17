package com.podoarena.dto;

import com.podoarena.constant.RepImgYn;
import com.podoarena.entity.GoodsImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class GoodsImgDto {
    private int id;

    private String imgName; // UUID로 바뀐 이미지 파일명

    private String oriImgName; // 원본 이미지 파일명

    private String imgUrl; // 이미지 경로

    private RepImgYn repImgYn; // 대표 이미지 여부(Y: 썸네일이미지, N: 일반이미지)

    private static ModelMapper modelMapper = new ModelMapper();

    // entity -> dto
    public static GoodsImgDto of(GoodsImg goodsImg) {
        return modelMapper.map(goodsImg, GoodsImgDto.class); // ItemImgDto 객체를 리턴
    }
}

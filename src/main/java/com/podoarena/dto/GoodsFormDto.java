package com.podoarena.dto;


import com.podoarena.constant.SellStatus;
import com.podoarena.constant.Sort;
import com.podoarena.entity.Goods;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodsFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력입니다.")
    private String goodsName;

    @NotNull(message = " 가격은 필수 입력입니다. ")
    private int goodsPrice;

    @NotNull(message = " 재고는 필수 입력입니다. ")
    private int goodsStock;

    private int goodsMaxAmount;

    private SellStatus sellStatus;

    private int count;

    private Sort sort;

    private List<GoodsImgDto> goodsImgDtoList = new ArrayList<>();

    private List<Long> goodsImgIds = new ArrayList<>();

    // modelMapper를 사용
    private static ModelMapper modelMapper = new ModelMapper();

    // dto -> entity
    public Goods createGoods(){
        return modelMapper.map(this, Goods.class); // Item entity 객체 리턴
    }

    // entity -> dto
    public static GoodsFormDto of(Goods goods){
        return modelMapper.map(goods, GoodsFormDto.class); // ItemFormDto 객체 리턴
    }






}

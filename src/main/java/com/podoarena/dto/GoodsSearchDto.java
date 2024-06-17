package com.podoarena.dto;

import com.podoarena.constant.SellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsSearchDto {
    private String searchQuery = "";
    private SellStatus searchSellStatus;
    private String searchBy;
    private String searchDateType;


}

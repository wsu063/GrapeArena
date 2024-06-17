package com.podoarena.dto;

import com.podoarena.constant.RepImgYn;
import com.podoarena.entity.ConcertImg;
import com.podoarena.entity.PlaceImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PlaceImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private RepImgYn repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // entity -> dto
    public static PlaceImgDto of(PlaceImg placeImg) {
        return modelMapper.map(placeImg, PlaceImgDto.class);
    }

}

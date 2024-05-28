package com.podoarena.dto;

import com.podoarena.constant.RepImgYn;
import com.podoarena.entity.ConcertImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ConcertImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private RepImgYn repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    // entity -> dto
    public static ConcertImgDto of(ConcertImg concertImg) {
        return modelMapper.map(concertImg, ConcertImgDto.class);
    }

}

package com.podoarena.dto;

import com.podoarena.constant.ConcertState;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Place;
import com.podoarena.entity.PlaceImg;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlaceFormDto {
    private Long id;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String placeName;

    private String placeLocation;

    private String placeBatch;

    private PlaceImgDto placeImgDto;

    private Long placeImgId;

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public Place createPlace() {
        Place place = new Place();
        place.setId(this.id);
        place.setPlaceName(this.placeName);
        place.setPlaceBatch(this.placeBatch);
        place.setPlaceLocation(this.placeLocation);
        if (this.placeImgDto != null) {
            PlaceImg placeImg = new PlaceImg();
            placeImg.setImgUrl(this.placeImgDto.getImgUrl());
            placeImg.setImgName(this.placeImgDto.getImgName());
            place.setPlaceImg(placeImg);
        }

        return place;
    }
    //entity -> dto
    public static PlaceFormDto of(Place place) {
        return modelMapper.map(place, PlaceFormDto.class);
    }
}

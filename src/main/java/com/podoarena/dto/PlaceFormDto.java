package com.podoarena.dto;

import com.podoarena.constant.ConcertState;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Place;
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

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public Place createPlace() {
        return modelMapper.map(this, Place.class);
    }
    //entity -> dto
    public static PlaceFormDto of(Place place) {
        return modelMapper.map(place, PlaceFormDto.class);
    }
}

package com.podoarena.service;

import com.podoarena.dto.PlaceFormDto;
import com.podoarena.dto.PlaceImgDto;
import com.podoarena.entity.Place;
import com.podoarena.entity.PlaceImg;
import com.podoarena.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceImgService placeImgService;

    //1. 공연장 등록
    public Long savePlace(PlaceFormDto placeFormDto, MultipartFile placeImgFile) throws Exception {
        Place place = placeFormDto.createPlace();
        placeRepository.save(place);

        PlaceImg placeImg = new PlaceImg();
        placeImg.setPlace(place);
        placeImgService.savePlaceImg(placeImg, placeImgFile);

        return placeFormDto.getId();
    }

    //2. 공연장 목록(관리자)
    public List<Place> getPlaceList() {
        List<Place> places = placeRepository.getPlaceAll();
        return null;
    }
}

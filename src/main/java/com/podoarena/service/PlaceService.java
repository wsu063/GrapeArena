package com.podoarena.service;

import com.podoarena.dto.PlaceFormDto;
import com.podoarena.dto.PlaceImgDto;
import com.podoarena.entity.Place;
import com.podoarena.entity.PlaceImg;
import com.podoarena.repository.PlaceImgRepository;
import com.podoarena.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PlaceImgRepository placeImgRepository;

    //1. 공연장 등록(관리자)
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
        return places;
    }

    //3. 공연장 수정(관리자)
    public Long updatePlace(PlaceFormDto placeFormDto, MultipartFile placeImgFile) throws Exception {
        Place place = placeRepository.findById(placeFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        Long placeImgId = placeFormDto.getPlaceImgId();
        place.updatePlace(placeFormDto);
        placeImgService.updatePlaceImg(placeImgId, placeImgFile);

        return place.getId();
    }

    @Transactional(readOnly = true)
    public PlaceFormDto getPlaceDtl(Long placeId) {

        //나중에 service에서 하나로 정리하기
        PlaceImg placeImg = placeImgRepository.findByPlaceId(placeId);
        PlaceImgDto placeImgDto = PlaceImgDto.of(placeImg);

        Place place = placeRepository.findById(placeId)
                .orElseThrow(EntityNotFoundException::new);

        PlaceFormDto placeFormDto = PlaceFormDto.of(place);

        placeFormDto.setPlaceImgDto(placeImgDto);


        return placeFormDto;
    }

    //4. 공연장 삭제(관리자)
    public void deletePlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(EntityNotFoundException::new);

        placeRepository.delete(place);
    }
}

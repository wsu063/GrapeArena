package com.podoarena.service;

import com.podoarena.dto.PlaceImgDto;
import com.podoarena.entity.PlaceImg;
import com.podoarena.repository.PlaceImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceImgService {
    @Value("${placeImgLocation}")
    private String placeImgLocation;

    private final PlaceImgRepository placeImgRepository;
    private final FileService fileService;

    //저장
    public void savePlaceImg(PlaceImg placeImg, MultipartFile placeImgFile) throws Exception {
        String oriImgName = placeImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if (!StringUtils.isEmpty(oriImgName)) { // 빈 문자열인지 아닌지 검사
            //빈 문자열이 아니면 업로드
            imgName = fileService.uploadFile(placeImgLocation,
                    oriImgName, placeImgFile.getBytes());
            //itemImgFile.getBytes(): 이미지 파일을 byte배열로 만들어준다.

            imgUrl = "/images/places/" + imgName;
        }
        //DB에 insert를 하기전 유저가 직접 입력하지 못하는 값들을 개발자가 넣어준다.
        placeImg.updatePlaceImg(oriImgName, imgName, imgUrl);
        placeImgRepository.save(placeImg); // insert
    }

    //가져오기
    public PlaceImgDto getPlaceImgDto(Long placeId) {
        PlaceImg placeImg = placeImgRepository.findByPlaceId(placeId);
        PlaceImgDto placeImgDto = PlaceImgDto.of(placeImg);

        return placeImgDto;
    }

    //이미지 수정
    public void updatePlaceImg(Long placeImgId, MultipartFile placeImgFile) throws  Exception {
        if(!placeImgFile.isEmpty()) {
            PlaceImg savedPlaceImg = placeImgRepository.findById(placeImgId)
                    .orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedPlaceImg.getImgName())) {
                fileService.deleteFile(placeImgLocation + "/" + savedPlaceImg.getImgName());
            }

            String oriImgName = placeImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(placeImgLocation, oriImgName, placeImgFile.getBytes());
            String imgUrl = "/images/places/" + imgName;

            savedPlaceImg.updatePlaceImg(oriImgName, imgName, imgUrl);

        }
    }
}

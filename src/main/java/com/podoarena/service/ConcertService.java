package com.podoarena.service;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.ConcertFormDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.ConcertImg;
import com.podoarena.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final ConcertImgService concertImgService;

    //1. 콘서트 등록
    public Long saveConcert(ConcertFormDto concertFormDto,
                            List<MultipartFile> concertImgFileList) throws Exception {
        //1. 콘서트 등록
        Concert concert = concertFormDto.createConcert();
        concertRepository.save(concert);

        //2. 이미지 등록
        for (int i = 0; i < concertImgFileList.size(); i++) {
            ConcertImg concertImg = new ConcertImg();
            concertImg.setConcert(concert);

            //첫번째 이미지 대표이미지로 지정
            if(i == 0) {
                concertImg.setRepImgYn(RepImgYn.Y);
            } else {
                concertImg.setRepImgYn(RepImgYn.N);
            }
            //이미지 파일 하나씩 저장
            concertImgService.saveConcertImg(concertImg, concertImgFileList.get(i));
        }


        return concertFormDto.getId();
    }



}

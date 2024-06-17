package com.podoarena.service;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.*;
import com.podoarena.entity.*;
import com.podoarena.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertService {
    // Concert, ReserveSeat Service
    private final ConcertRepository concertRepository;
    private final ReserveSeatRepository reserveSeatRepository;
    private final ConcertImgService concertImgService;
    private final DateService dateService;

    private final PlaceConcertService placeConcertService;
    private final PlaceConcertRepository placeConcertRepository;

    //1. 콘서트 등록
    public Long saveConcert(ConcertFormDto concertFormDto,
                            List<MultipartFile> concertImgFileList) throws Exception {
        //1. 콘서트 등록
        Concert concert = concertFormDto.createConcert();
        concertRepository.save(concert);

        concertFormDto.setId(concert.getId());

        placeConcertService.savePlaceConcert(concertFormDto);


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
        concertFormDto.setId(concert.getId());

        return concertFormDto.getId();
    }

    //2. 콘서트 상세페이지
    @Transactional
    public ConcertFormDto getConcertDtl(Long concertId) {
        //Concert 가져오기
        Concert concert = concertRepository.findById(concertId).orElseThrow(EntityNotFoundException::new);
        ConcertFormDto concertFormDto = ConcertFormDto.of(concert);

        //필요한 요소를 가져온다.
        List<ConcertImgDto> concertImgList = concertImgService.getConcertImgDtoList(concertId);
        List<Date> dateList = dateService.getDateByPlaceConcertId(concert.getPlaceConcert().getId());
        PlaceFormDto placeFormDto = PlaceFormDto.of(concert.getPlaceConcert().getPlace());

        //가져온 concertFormDto에 이미지 리스트를 넣어준다.
        concertFormDto.setConcertImgDtoList(concertImgList);
        //가져온 concertFormDto에 date를 세팅한다.
        concertFormDto.setDates(dateList);
        //가져온 concertFormDto에 place를 세팅한다.
        concertFormDto.getPlaceFormDtoList().add(placeFormDto);


        return concertFormDto;
    }

    //3. 콘서트 수정
    public Long updateConcert(ConcertFormDto concertFormDto, List<MultipartFile> concertImgFileList) throws Exception {
        // concert 수정
        Concert concert = concertRepository.findById(concertFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        concert.updateConcert(concertFormDto);

        // concertImg 수정
        List<Long> concertImgIds = concertFormDto.getConcertImgIds();

        for (int i = 0; i < concertImgFileList.size(); i++) {
            concertImgService.updateConcertImg(concertImgIds.get(i), concertImgFileList.get(i));
        }

        // Date 수정
        List<Long> dateIds = concertFormDto.getDateIds();
        for(int i = 0; i < dateIds.size(); i++) {
            dateService.updateDate(dateIds.get(i), concertFormDto.getDateList().get(i));
        }
        return concert.getId();
    }



    // 4. 콘서트 삭제하기
    public void deleteConcert(Long concertId) {
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(EntityNotFoundException::new);

        concertRepository.delete(concert);
        PlaceConcert placeConcert = concert.getPlaceConcert();
        placeConcert.getPlace().getPlaceConcertList().remove(placeConcert);
        placeConcertRepository.delete(placeConcert);

    }

    // 콘서트 리스트 가져오기
    public List<Concert> getConcertList() {
        List<Concert> concerts = concertRepository.getConcertList();

        return concerts;
    }

    // 콘서트 페이징 리스트 가져오기
    public Page<Concert> getAdminConcertPage(ConcertSearchDto concertSearchDto, Pageable pageable) {
        Page<Concert> getAdminConcertPage = concertRepository.getAdminConcertPage(concertSearchDto, pageable);

        return getAdminConcertPage;
    }



    // 예매내역 가져오기(관리자)
    public Page<ReserveSeat> getReserveSeatPage(ReserveSeatSearchDto reserveSeatSearchDto, Pageable pageable) {
        Page<ReserveSeat> reserveSeatPage = reserveSeatRepository.getReserveSeatPage(reserveSeatSearchDto, pageable);

        return reserveSeatPage;
    }

    public Concert getConcert(Long concertId) {
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(EntityNotFoundException::new);

        return concert;
    }
}

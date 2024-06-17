package com.podoarena.service;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Date;
import com.podoarena.entity.Place;
import com.podoarena.entity.PlaceConcert;
import com.podoarena.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceConcertService {
    private final PlaceConcertRepository placeConcertRepository;
    private final PlaceRepository placeRepository;
    private final ConcertRepository concertRepository;
    private final DateRepository dateRepository;
    private final SeatService seatService;


    //1. PC 등록
    public Long savePlaceConcert(ConcertFormDto concertFormDto) throws Exception {

        PlaceConcert placeConcert = new PlaceConcert();

        Place place = placeRepository.findById(concertFormDto.getPlaceId())
                        .orElseThrow(EntityNotFoundException::new);

        Concert concert = concertRepository.findById(concertFormDto.getId())
                        .orElseThrow(EntityNotFoundException::new);

        List<Date> dateList = new ArrayList<>();

        List<LocalDateTime> sortDateList = concertFormDto.getDateList();
        Collections.sort(sortDateList); // 날짜가 저장될때 오름차순으로 정렬된 이후 저장된다.

        // 선택한 콘서트의 공연장, 날짜에 따라 placeConcert를 만들고, DateList를 만든다.
        for (LocalDateTime time : sortDateList) {
            Date date = new Date();
            date.setDateTime(time);
            dateList.add(date);
            dateRepository.save(date); // date 저장
        }

        placeConcert.createPlaceConcert(place, concert, dateList);
        placeConcertRepository.save(placeConcert); // placeConcert 저장
        placeConcert.setPlace(place);
        placeConcert.setConcert(concert);

        // 생긴 날짜마다, 좌석을 생성해준다.
        for(Date date : dateList) {
            date.setPlaceConcert(placeConcert);
            //좌석 생성
            seatService.createSeatList(placeConcert, date);

        }
        place.getPlaceConcertList().add(placeConcert);
        concert.setPlaceConcert(placeConcert);


        return placeConcert.getId();
    }



}

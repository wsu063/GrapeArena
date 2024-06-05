package com.podoarena.service;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Date;
import com.podoarena.entity.Place;
import com.podoarena.entity.PlaceConcert;
import com.podoarena.repository.ConcertRepository;
import com.podoarena.repository.DateRepository;
import com.podoarena.repository.PlaceConcertRepository;
import com.podoarena.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceConcertService {
    private final PlaceConcertRepository placeConcertRepository;
    private final PlaceRepository placeRepository;
    private final ConcertRepository concertRepository;
    private final DateRepository dateRepository;

    //1. PC 등록
    public Long savePlaceConcert(ConcertFormDto concertFormDto) throws Exception {

        PlaceConcert placeConcert = new PlaceConcert();

        Place place = placeRepository.findById(concertFormDto.getPlaceId())
                        .orElseThrow(EntityNotFoundException::new);

        Concert concert = concertRepository.findById(concertFormDto.getId())
                        .orElseThrow(EntityNotFoundException::new);

        List<Date> dateList = new ArrayList<>();

        for(LocalDateTime time : concertFormDto.getDateList()) {
            Date date = new Date();
            date.setDateTime(time);
            dateList.add(date);
            dateRepository.save(date); // date 저장
        }

        placeConcert.createPlaceConcert(place, concert, dateList);
        placeConcertRepository.save(placeConcert); // placeConcert 저장
        placeConcert.setPlace(place);
        placeConcert.setConcert(concert);


        for(Date date : dateList) {
            date.setPlaceConcert(placeConcert);
        }
        place.setPlaceConcert(placeConcert);
        concert.setPlaceConcert(placeConcert);


        return placeConcert.getId();
    }
}

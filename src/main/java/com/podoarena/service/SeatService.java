package com.podoarena.service;

import com.podoarena.entity.Date;
import com.podoarena.entity.PlaceConcert;
import com.podoarena.entity.Seat;
import com.podoarena.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seat> createSeatList(PlaceConcert placeConcert, Date date) {
        String batch = placeConcert.getPlace().getPlaceBatch();
        // batch를 1,1,0;2,1,1;1,0,1 형식으로 입력받아서 좌석배치한다.
        String[] divide = batch.split(";");
        int rows = divide.length;

        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            String[] seatStatuses = divide[i].split(",");
            for (int j = 0; j < seatStatuses.length; j++) {
                int seatStatus = Integer.parseInt(seatStatuses[j]);
                Seat seat = new Seat(placeConcert, i, j, seatStatus, date);
                seatList.add(seat);
            }
        }
        seatRepository.saveAll(seatList);


        return seatList;
    }

    public List<Seat> getSeatList(Long dateId) {
        return seatRepository.findByDateIdOrderByIdAsc(dateId);
    }

}

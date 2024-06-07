package com.podoarena.service;

import com.podoarena.entity.Date;
import com.podoarena.repository.DateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DateService {
    private final DateRepository dateRepository;

    // 날짜 수정
    public void updateDate(Long dateId, LocalDateTime localDateTime) {
        Date date = dateRepository.findById(dateId)
                .orElseThrow(EntityNotFoundException::new);

        date.setDateTime(localDateTime);
    }

    //날짜 받아오기
    public List<Date> getDateByConcertId(Long placeConcertId) {
        return dateRepository.getDateByConcertId(placeConcertId);
    }
}

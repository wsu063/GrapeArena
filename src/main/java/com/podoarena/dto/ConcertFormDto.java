package com.podoarena.dto;

import com.podoarena.constant.ConcertState;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Date;
import com.podoarena.entity.Place;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConcertFormDto {
    private Long id;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String concertName;

    private String concertSinger;

    private ConcertState concertState;

    private List<ConcertImgDto> concertImgDtoList = new ArrayList<>();

    private List<Long> concertImgIds = new ArrayList<>(); // 이미지 아이디 저장(수정할때씀)

    private List<LocalDateTime> dateList = new ArrayList<>();

    private List<Date> dates = new ArrayList<>();

    private List<Long> dateIds = new ArrayList<>();

    private List<PlaceFormDto> placeFormDtoList = new ArrayList<>();

    private Long placeId;

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public Concert createConcert() {
        Concert concert = new Concert();
        concert.setConcertName(this.concertName);
        concert.setConcertSinger(this.concertSinger);
        concert.setConcertState(this.concertState);

        return concert;
    }
    //entity -> dto
    public static ConcertFormDto of(Concert concert) {
        return modelMapper.map(concert, ConcertFormDto.class);
    }
}

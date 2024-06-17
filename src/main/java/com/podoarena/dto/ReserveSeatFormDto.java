package com.podoarena.dto;

import com.podoarena.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReserveSeatFormDto {
    //예매를 위해서 만든 폼
    private Long id;

    private Seat seat;

    private Reserve reserve;

    private PlaceConcert placeConcert;
    
    private Date date; // 예매하기위해서 필요한 데이터

    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public ReserveSeat createReserve() {
        ReserveSeat reserveSeat = new ReserveSeat();
        reserveSeat.setSeat(this.seat);
        reserveSeat.setReserve(this.reserve);
        reserveSeat.setPlaceConcert(this.placeConcert);

        return reserveSeat;

    }

    // entity -> dto
    public static ReserveSeatFormDto of(ReserveSeat ReserveSeat) {
        return modelMapper.map(ReserveSeat, ReserveSeatFormDto.class);
    }
}

package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.dto.ReserveSeatFormDto;
import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.*;
import com.podoarena.service.ConcertService;
import com.podoarena.service.DateService;
import com.podoarena.service.ReserveSeatService;
import com.podoarena.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveSeatService reserveSeatService;
    private final ConcertService concertService;
    private final DateService dateService;
    private final SeatService seatService;

    // 콘서트 예매 내역 이동
    @GetMapping(value="/reserves/reserveDtl")
    public String reserveDtl(Model model){
//        model.addAttribute("reserveFormDto", new ReserveFormDto());
        return "reserve/reserveDtl";
    }


    // 1. 콘서트 예약 페이지로 이동
    @GetMapping(value ="/reserves/reserveTime/{concertId}")
    public String reserveTime(@PathVariable("concertId") Long concertId, Model model) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
        model.addAttribute("concertFormDto", concertFormDto);
        return "reserve/reserveTime";
    }
    // 2. 회차를 선택하고 좌석을 선택하는 페이지로 이동
    @PostMapping(value ="/reserves/reserveSeat/{concertId}")
    public String reserveSeat(@PathVariable("concertId") Long concertId,
                              @RequestParam("dateId") Long dateId,
                              Model model) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
        //선택한 dateId로 좌석 정보를 찾는다.
        List<Seat> seats = seatService.getSeatList(dateId);

        model.addAttribute("concertFormDto", concertFormDto);
        model.addAttribute("seats", seats);
        return "reserve/reserveSeat";
    }

    // 3. 좌석을 선택하고 결제방식을 선택하는 페이지로 이동
    @PostMapping(value ="/reserves/reservePay/{concertId}")
    public String reservePay(@PathVariable("concertId") Long concertId,
                             @RequestParam("seatId") Long seatId,
                             Model model) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
        //선택한 seatId로 좌석 정보를 찾는다.
        Seat seat = seatService.getSeat(seatId);

        model.addAttribute("concertFormDto", concertFormDto);
        model.addAttribute("seat", seat);

        return "reserve/reservePay";
    }

    @PostMapping(value = "/reserves/new/{concertId}")
    public String reserve(@Valid ReserveSeatFormDto reserveSeatFormDto, BindingResult bindingResult, Model model) {
        //responseBody로 해서 화면따로? 아니면 한 HTML에서 전부다 할까?
        if(bindingResult.hasErrors()){
            return "reserve/reservePay";
        }
        return null;
    }





    // 고객 관리 페이지로 이동
    @GetMapping(value = {"/admin/reserves/list", "/admin/reserves/list/{page}"})
    public String ReserveConcertPage(ReserveSeatSearchDto reserveSeatSearchDto, @PathVariable("page")Optional<Integer> page,
                                     Model model) {
        //page변수가 존재하면 가져오고, 없으면 0. 목록은 최대 10개 페이지
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<ReserveSeat> reserveSeatPage = reserveSeatService.getReserveSeatPage(reserveSeatSearchDto, pageable);

        model.addAttribute("reserveSeatPage", reserveSeatPage);
        model.addAttribute("reserveSeatSearchDto", reserveSeatSearchDto);

        model.addAttribute("maxPage", 5);

        return "admin/reserveSeatList";}
}

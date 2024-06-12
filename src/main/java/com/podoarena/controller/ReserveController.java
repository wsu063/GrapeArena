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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveSeatService reserveSeatService;
    private final ConcertService concertService;
    private final DateService dateService;
    private final SeatService seatService;


    // 콘서트 예약 페이지 이동
    @GetMapping(value ="/reserves/reserveTime/{concertId}")
    public String reserveTime(@PathVariable("concertId") Long concertId, Model model) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
        model.addAttribute("concertFormDto", concertFormDto);
        model.addAttribute("reserveFormDto", new ReserveSeatFormDto());
        return "reserve/reserveTime";
    }

    @GetMapping(value ="/reserves/reservePay/{concertId}")
    public String reservePay(@RequestParam("reserveFormDto") ReserveSeatFormDto reserveSeatFormDto,
                             Model model) {
        model.addAttribute("reserveFormDto", new ReserveSeatFormDto());
        return "reserve/reservePay";
    }

    @GetMapping(value ="/reserves/reserveSeat/{concertId}")
    public String reserveSeat(@PathVariable("concertId") Long concertId,
                               Model model) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
        // @RequestParam("dateId") Long dateId,
        // Date date = dateService.getDateByPlaceConcertId(concertFormDto.getPlaceId());
        Long dateId = 16L; // 임시로 넣은 데이트값
        List<Seat> seats = seatService.getSeatList(dateId);

        model.addAttribute("concertFormDto", concertFormDto);
        model.addAttribute("seats", seats);
        model.addAttribute("reserveFormDto", new ReserveSeatFormDto());
        return "reserve/reserveSeat";
    }


    @GetMapping(value = "/reserves/new/{concertId}")
    public String reservePay(@PathVariable("concertId") Long concertId,
                              Model model) {
        // 콘서트에서 예매하기 누르면 예매하는 화면으로 이동한다.
        // (고민중) dateTime을 받아온다.

        // 콘서트, 날짜(회차) 선택 -> 좌석 선택 -> 결제하기
        // 그러면 콘서트 상세페이지에서 제공하는 달력은 회차만 보여줘야되나? 의미가 없나? 고민해볼것
        // 혹은 데이터 입력 받고, 거기서도 바꿀 수 잇게 하기.

        ReserveSeatFormDto reserveSeatFormDto = new ReserveSeatFormDto();
        // 예약하기위해서 필요한것: 좌석, 공연장콘서트, 공연장콘서트날짜,

        Concert concert = concertService.getConcert(concertId);


        model.addAttribute("reserveFormDto", reserveSeatFormDto);
        model.addAttribute("concert", concert);

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

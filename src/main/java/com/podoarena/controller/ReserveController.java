package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.dto.ReserveSeatFormDto;
import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.*;
import com.podoarena.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveSeatService reserveSeatService;
    private final ConcertService concertService;
    private final DateService dateService;
    private final SeatService seatService;
    private final MemberService memberService;

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

    // 4. 결제방식 제출 후 결제
    @PostMapping(value = "/reserves/reserve/{concertId}")
    public ResponseEntity<String> reserveInsert(@PathVariable("concertId") Long concertId,
                                        @RequestBody Map<String, Long> requestData, Principal principal) {
        Long seatId = requestData.get("seatId");
        Seat seat = seatService.getSeat(seatId);
        Member member = memberService.getMember(principal.getName());
        PlaceConcert placeConcert = concertService.getConcert(concertId).getPlaceConcert();
        try {
            reserveSeatService.saveReserveSeat(seat,member, placeConcert);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("결제 성공했습니다", HttpStatus.OK);
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

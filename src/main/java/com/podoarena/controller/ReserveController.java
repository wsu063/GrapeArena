package com.podoarena.controller;

import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.ReserveSeat;
import com.podoarena.repository.ReserveSeatRepository;
import com.podoarena.service.ReserveSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveSeatService reserveSeatService;

    // 고객 관리 페이지로 이동
    @GetMapping(value = {"/admin/reserve/list", "/admin/reserve/list/{page}"})
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

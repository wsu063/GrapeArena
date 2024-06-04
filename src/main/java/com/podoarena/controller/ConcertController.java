package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.dto.ReserveSeatSearchDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.ReserveSeat;
import com.podoarena.entity.Seat;
import com.podoarena.service.ConcertService;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    //콘서트 등록
    @GetMapping(value = "/admin/concerts/new")
    public String concertForm(Model model) {
        model.addAttribute("concertFormDto", new ConcertFormDto());
        return "admin/concertForm";
    }

    // 콘서트 등록 처리

    @PostMapping(value = "/admin/concerts/new")
    public String concertNew(@Valid ConcertFormDto concertFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("concertImgFile") List<MultipartFile> concertImgFileList) {

        if (bindingResult.hasErrors()) return "admin/concertForm";

        if(concertImgFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage",
                    "대표 이미지는 필수 입력입니다.");

            return "admin/concertForm";
        }
        try {
            concertService.saveConcert(concertFormDto, concertImgFileList);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "콘서트 등록 중 에러가 발생했습니다.");
            return "admin/concertForm";
        }
        return "redirect:/";
    }


    //콘서트 상세 페이지
    @GetMapping(value = "/concerts/detail/{concertId}")
    private String concertDtl(Model model, @PathVariable(value = "concertId") Long concertId, Principal principal) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);

        model.addAttribute("concert", concertFormDto);
        return "concert/concertDtl";
    }

    //임시 상세 페이지(db 만들고 나서 지우기)
    @GetMapping(value = "/concerts/detail")
    private String concertDtltemp(Model model, Principal principal) {

        return "concert/concertDtl";
    }

    //콘서트 수정
    @GetMapping(value = "/admin/concerts/rewrite/{concertId}")
    private String concertRewrite(@PathVariable("concertId") Long concertId, Model model) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘서트 정보를 가져오는 도중 에러가 발생했습니다.");

            //에러 발생시 비어있는 객체를 넘긴다
            model.addAttribute("concertFormDto", new ConcertFormDto());
            return "admin/concertModifyForm";
        }
        return "admin/concertModifyForm";
    }

    //콘서트 예매 내역 (유저)
    @GetMapping(value = "/concerts/list")
    public String concertReserveList(Model model) {
        List<Concert> concerts = concertService.getConcertList();
        model.addAttribute("concerts", concerts);

        return "concerts/list";
    }

    //콘서트 예매 내역 (관리자)
    @GetMapping(value = {"/admin/concerts/list", "/admin/concerts/list/{page}"})
    public String concertReserveListAdmin(ReserveSeatSearchDto reserveSeatSearchDto,
                                          @PathVariable("page")Optional<Integer> page, Model model) {
        // page가 있으면 번호 조회, 아니면 0
        // 한 페이지에 10개
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        
        Page<ReserveSeat> reserveSeats = concertService.getReserveSeatPage(reserveSeatSearchDto, pageable);

        model.addAttribute("reserveSeats", reserveSeats);
        model.addAttribute("reserveSeatSearchDto", reserveSeatSearchDto);
        
        //최대 페이지 번호 5개씩
        model.addAttribute("maxPage", 5);

        return "admin/concertMng"; // 임시
    }


}

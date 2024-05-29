package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.entity.Concert;
import com.podoarena.service.ConcertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    //콘서트 등록
    @GetMapping(value = "/admin/concerts/new")
    public String concertWrite(Model model) {
        model.addAttribute("concertFormDto", new ConcertFormDto());
        return "concert/concertForm";
    }

    @PostMapping(value = "/admin/concerts/new")
    public String concertInsert(@Valid ConcertFormDto concertFormDto, BindingResult bindingResult,
                                Model model, @RequestParam("concertImgFile") List<MultipartFile> concertImgFileList) {
        if (bindingResult.hasErrors()) return "concert/concertForm";

        try {
            concertService.saveConcert(concertFormDto, concertImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘서트 등록 중 에러가 발생했습니다.");
            return "admin/concert/concertForm";
        }
        //등록하고 메인화면으로 돌아간다.
        //다른 화면으로 해야할 필요가 있는가?
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
    @GetMapping(value = "/admin/concerts/list")
    public String concertReserveListAdmin(Model model) {

        return "admin/concertMng"; // 임시
    }


}

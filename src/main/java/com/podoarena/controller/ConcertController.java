package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
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

    @PostMapping
    public String concertInsert(@Valid ConcertFormDto concertFormDto, BindingResult bindingResult,
                                Model model, @RequestParam("concertImgFile") List<MultipartFile> concertImgFileList) {
        if (bindingResult.hasErrors()) return "concert/concertForm";

        try {
            concertService.saveConcert(concertFormDto, concertImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘서트 등록 중 에러가 발생했습니다.");
            return "concert/concertForm";
        }
        //등록하고 메인화면으로 돌아간다.
        //다른 화면으로 해야할 필요가 있는가?
        return "redirect:/";
    }

    //콘서트 상세 페이지



    //콘서트 수정
    @GetMapping(value = "/admin/concerts/rewrite/{concertId}")
    private String concertRewrite(@PathVariable("concertId") Long lecture) {

        return "concert/concertModifyForm";
    }



}

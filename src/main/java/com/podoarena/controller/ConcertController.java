package com.podoarena.controller;

import com.podoarena.dto.ConcertFormDto;
import com.podoarena.dto.ConcertSearchDto;
import com.podoarena.dto.PlaceFormDto;
import com.podoarena.dto.ReserveFormDto;
import com.podoarena.entity.Concert;
import com.podoarena.entity.Date;
import com.podoarena.entity.Place;
import com.podoarena.service.ConcertService;
import com.podoarena.service.PlaceService;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;
    private final PlaceService placeService;

    //콘서트 등록
    @GetMapping(value = "/admin/concerts/new")
    public String concertForm(Model model) {
        ConcertFormDto concertFormDto = new ConcertFormDto();

        List<Place> places = placeService.getPlaceList();

        for(Place place : places) {
            PlaceFormDto placeFormDto = PlaceFormDto.of(place);
            concertFormDto.getPlaceFormDtoList().add(placeFormDto);
        }
        model.addAttribute("concertFormDto", concertFormDto);
        return "admin/concertForm";
    }

    // 콘서트 등록 처리
    @PostMapping(value = "/admin/concerts/new")
    public String concertNew(@Valid ConcertFormDto concertFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("dateTime") List<LocalDateTime> dateTimeList,
                             @RequestParam("placeId") Long placeId,
                             @RequestParam("concertImgFile") List<MultipartFile> concertImgFileList) {

        if (bindingResult.hasErrors()) return "admin/concertForm";

        if(concertImgFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage",
                    "대표 이미지는 필수 입력입니다.");

            return "admin/concertForm";
        }
        try {
            concertFormDto.setPlaceId(placeId);
            for(LocalDateTime dateTime : dateTimeList) {
                concertFormDto.getDateList().add(dateTime);
            }

            concertService.saveConcert(concertFormDto, concertImgFileList);

        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "콘서트 등록 중 에러가 발생했습니다.");
            return "admin/concertForm";
        }
        return "redirect:/admin/concerts/list";
    }


    //콘서트 수정
    @GetMapping(value = "/admin/concerts/edit/{concertId}")
    private String concertModify(@PathVariable("concertId") Long concertId, Model model) {

        try {
            ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);
            List<Place> places = placeService.getPlaceList();

            for(Place place : places) {
                PlaceFormDto placeFormDto = PlaceFormDto.of(place);
                //이미 선택한 place라면 패스한다.
                if(concertFormDto.getPlaceFormDtoList().get(0).getId().equals(placeFormDto.getId())) {
                    continue;
                }

                concertFormDto.getPlaceFormDtoList().add(placeFormDto);
            }

            model.addAttribute("concertFormDto", concertFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘서트 정보를 가져오는 도중 에러가 발생했습니다.");
            //에러 발생시 비어있는 객체를 넘긴다
            model.addAttribute("concertFormDto", new ConcertFormDto());
            return "admin/concertModifyForm";
        }
        return "admin/concertModifyForm";
    }
    //콘서트 수정
    @PostMapping(value = "/admin/concerts/edit/{concertId}")
    public String concertUpdate(@Valid ConcertFormDto concertFormDto, Model model,
                              BindingResult bindingResult, @RequestParam("concertImgFile") List<MultipartFile> concertImgFileList,
                              @PathVariable("concertId") Long concertId) {
        if (bindingResult.hasErrors()) return "admin/placeForm";

        ConcertFormDto getConcertFormDto = concertService.getConcertDtl(concertId);
        List<Place> places = placeService.getPlaceList();

        for(Place place : places) {
            PlaceFormDto placeFormDto = PlaceFormDto.of(place);
            getConcertFormDto.getPlaceFormDtoList().add(placeFormDto);
        }
        try {
            concertService.updateConcert(concertFormDto, concertImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "공연장 수정 중 오류가 발생했습니다.");
            model.addAttribute("concertFormDto", getConcertFormDto);

            return "admin/concertModifyForm";
        }

        return "redirect:/admin/concerts/list";
    }

    @DeleteMapping(value = "/admin/concerts/delete/{concertId}")
    private @ResponseBody ResponseEntity concertDelete(@PathVariable("concertId") Long concertId) {

        concertService.deleteConcert(concertId);

        return new ResponseEntity<Long>(concertId, HttpStatus.OK);
    }

    //콘서트 목록 (관리자)
    @GetMapping(value = {"/admin/concerts/list", "/admin/concerts/list/{page}"})
    public String concertReserveListAdmin(ConcertSearchDto concertSearchDto,
                                          @PathVariable(value = "page") Optional<Integer> page, Model model) {
        // page가 있으면 번호 조회, 아니면 0
        // 한 페이지에 10개
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Concert> concertPage = concertService.getAdminConcertPage(concertSearchDto, pageable);

        model.addAttribute("concertPage", concertPage);
        model.addAttribute("concertSearchDto", concertSearchDto);

        //최대 페이지 번호 5개씩
        model.addAttribute("maxPage", 5);

        return "admin/concertList";
    }

    //콘서트 예매 내역 (유저)
    @GetMapping(value = "/concerts/list")
    public String concertReserveList(Model model) {
        List<Concert> concerts = concertService.getConcertList();
        model.addAttribute("concerts", concerts);

        return "concerts/list";
    }

    //콘서트 상세 페이지
    @GetMapping(value = "/concerts/detail/{concertId}")
    private String concertDtl(Model model, @PathVariable(value = "concertId") Long concertId) {
        ConcertFormDto concertFormDto = concertService.getConcertDtl(concertId);

        model.addAttribute("concertFormDto", concertFormDto);

        return "concert/concertDtl";
    }
}

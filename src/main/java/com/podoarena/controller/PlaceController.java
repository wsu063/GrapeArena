package com.podoarena.controller;

import com.podoarena.dto.PlaceFormDto;
import com.podoarena.entity.Place;
import com.podoarena.entity.ReserveSeat;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    //공연장 목록
    @GetMapping(value = "/admin/places/list")
    public String placeList(Model model) {

        List<Place> placeList = placeService.getPlaceList();

        model.addAttribute("placeList", placeList);

        return "admin/placeList";
    }
    
    //공연장 등록
    @GetMapping(value = "/admin/places/new")
    public String placeInsert(Model model) {
        model.addAttribute("placeFormDto", new PlaceFormDto());
        return "admin/placeForm";
    }

    @PostMapping(value = "/admin/places/new")
    public String placeNew(@Valid PlaceFormDto placeFormDto, BindingResult bindingResult, Model model,
                           @RequestParam("placeImgFile")MultipartFile placeImgFile) {
        if(bindingResult.hasErrors()) return "admin/placeForm";

        try {
            placeService.savePlace(placeFormDto, placeImgFile);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "공연장 등록 중 에러가 발생했습니다.");
            return "admin/placeForm";
        }
        return "redirect:/admin/places/list";
    }

    //공연장 수정
    @GetMapping(value = "/admin/places/edit")
    public String placeModify() {


        return "admin/placeModifyForm";
    }

    //공연장 삭제
    @DeleteMapping(value = "/admin/places/delete/{placeId}")
    public @ResponseBody ResponseEntity deletePlace(@PathVariable("placeId") Long placeId, Principal principal) {

        placeService.deletePlace(placeId);

        return new ResponseEntity<Long>(placeId, HttpStatus.OK);
    }
}

package com.podoarena.controller;

import com.podoarena.entity.Place;
import com.podoarena.entity.ReserveSeat;
import com.podoarena.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    //공연장 목록
    @GetMapping(value = {"/admin/places/list", "/admin/places/list/{page}"})
    public String placeList(Model model) {

        List<Place> placeList = placeService.getPlaceList();

        model.addAttribute("placeList", placeList);

        return "admin/placeList";
    }
    
    //공연장 등록
    @GetMapping(value = "/admin/placeForm")
    public String placeInsert() {


        return "admin/placeForm";
    }

    //공연장 수정
    @GetMapping(value = "/admin/placeModifyForm")
    public String placeModify() {


        return "admin/placeModifyForm";
    }
}

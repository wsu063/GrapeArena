package com.podoarena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    //공연장 목록
    @GetMapping(value = "/admin/placeList")
    public String placeList() {


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

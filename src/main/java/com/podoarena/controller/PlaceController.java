package com.podoarena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PlaceController {

    @GetMapping(value = "/admin/placeList")
    public String placeList() {


        return "admin/placeList";
    }

    @GetMapping(value = "/admin/placeForm")
    public String placeInsert() {


        return "admin/placeForm";
    }

    @GetMapping(value = "/admin/placeModifyForm")
    public String placeModify() {


        return "admin/placeModifyForm";
    }
}

package com.podoarena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    // 관리자 페이지 이동
    @GetMapping(value = "/admin/bookingMng")
    public String adminPage() { return "admin/bookingMng";}

    // 관리자 페이지 이동
    @GetMapping(value = "/admin/concertMng")
    public String concertMng() { return "admin/concertMng";}

    // 관리자 페이지 이동
    @GetMapping(value = "/admin/concertModifyForm")
    public String concertModifyForm() { return "admin/concertModifyForm";}

    // 관리자 페이지 이동
    @GetMapping(value = "/admin/concertForm")
    public String concertForm() { return "admin/concertForm";}

    @GetMapping(value = "/admin/goodsForm")
    public String goodsForm() { return "admin/goodsForm";}

}

package com.podoarena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class ReserveController {
    // 관리자 페이지 이동
    @GetMapping(value = "/admin/bookingMng")
    public String adminPage() { return "admin/bookingMng";}
}

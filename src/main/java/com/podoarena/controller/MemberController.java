package com.podoarena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @GetMapping(value = "/members/login")
    public String loginpage () {
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/members/register")
    public String registerPage() {
        return "member/memberForm";
    }
}

package com.podoarena.controller;

import com.podoarena.dto.ShopFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GoodsController {

    // 굿즈 페이지 이동
    @GetMapping(value = "/goods/goodsList")
    public String goodspage() {
        return "goods/goodsList";
    }
}

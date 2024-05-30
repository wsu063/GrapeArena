package com.podoarena.controller;

import com.podoarena.dto.GoodsFormDto;
import com.podoarena.service.GoodsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    // 굿즈 메인페이지 이동
    @GetMapping(value = "/goods/goodsIndex")
    public String goodsPage() {
        return "goods/goodsIndex";
    }

    // 굿즈 상세 페이지 이동
    @GetMapping(value = "/goods/goodsDtl")
    public String goodsDtl() {
        return "goods/goodsDtl";
    }

    @GetMapping(value ="/admin/goodsForm")
    public String goodsForm(Model model) {
        GoodsFormDto goodsFormDto = new GoodsFormDto();
        model.addAttribute("goodsFormDto", goodsFormDto);

        return "admin/goodsForm";
    }

    // 굿즈 등록 처리
    @PostMapping(value ="/admin/goodsForm")
    public String goodsNew(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult, Model model,
                           @RequestParam("goodsImgFile")List<MultipartFile> goodsImgFileList){
        if(bindingResult.hasErrors()) return "admin/goodsForm";

        if(goodsImgFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage",
                    "대표 이미지는 필수 입력입니다.");

            return "admin/goodsForm";
        }

        try {
            goodsService.saveGoods(goodsFormDto, goodsImgFileList);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품 등록 중 에러가 발생했습니다.");
            return "admin/goodsForm";
        }
        return "redirect:/";
    }

}

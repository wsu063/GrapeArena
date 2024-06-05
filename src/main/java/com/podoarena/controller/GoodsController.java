package com.podoarena.controller;

import com.podoarena.dto.GoodsFormDto;
import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.entity.Goods;
import com.podoarena.service.GoodsService;
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
public class GoodsController {
    private final GoodsService goodsService;

    // 굿즈 메인페이지 이동
    @GetMapping(value = "/goods/goodsIndex")
    public String goodsPage(Model model, GoodsSearchDto goodsSearchDto,
                            @RequestParam(value = "page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 20);
        Page<MainGoodsDto> goods = goodsService.getMainGoodsList(goodsSearchDto, pageable);

        model.addAttribute("goods", goods);
        model.addAttribute("goodsSearchDto", goodsSearchDto);
        model.addAttribute("maxPage", 5);

        return "goods/goodsIndex";
    }

    // 굿즈 상세 페이지 이동
    @GetMapping(value = "/goods/goodsDtl/{goodId}")
    public String goodsDtl(Model model, @PathVariable(value ="goodId") Long id) {
    //GoodsFormDto goods = goodsService.g
        return "goods/goodsDtl";
    }

    @GetMapping(value ="/admin/goods/new")
    public String goodsForm(Model model) {
        model.addAttribute("goodsFormDto", new GoodsFormDto());
        return "admin/goodsForm";
    }

    // 굿즈 등록 처리
    @PostMapping(value ="/admin/goods/new")
    public String goodsNew(@Valid GoodsFormDto goodsFormDto, BindingResult bindingResult, Model model,
                           @RequestParam("goodsImgFile")List<MultipartFile> goodsImgFileList){

        if (bindingResult.hasErrors()) return "admin/goodsForm";

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
        return "admin/goodsList";
    }




    //굿즈 삭제
    @DeleteMapping(value = "/admin/goodsDelete/{goodsId}")
    public @ResponseBody ResponseEntity deleteGoods(@PathVariable(value = "goodsId") Long goodsId) {
        goodsService.deleteGoods(goodsId);
        return new ResponseEntity<Long>(goodsId, HttpStatus.OK);
    }

    // 굿즈 리스트 이동
    @GetMapping(value = {"/admin/goodsList", "/admin/goodsList/{page}"})
    public String goodsList(Model model, GoodsSearchDto goodsSearchDto,
                            @PathVariable(value = "page")Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);

        Page<Goods> goods = goodsService.getAdminGoodsPage(goodsSearchDto, pageable);

        model.addAttribute("posts", goods );
        model.addAttribute("goodsFormDto", new GoodsFormDto());
        model.addAttribute("maxPage", 5);

        return "admin/goodsList";
    }

}

package com.podoarena.controller;

import com.podoarena.dto.GoodsFormDto;
import com.podoarena.dto.GoodsSearchDto;
import com.podoarena.dto.MainGoodsDto;
import com.podoarena.dto.PlaceFormDto;
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
    @GetMapping(value = {"/goods/goodsIndex", "/goods/goodsIndex/{page}"})
    public String goodsPage(Model model, GoodsSearchDto goodsSearchDto,
                            @PathVariable(value = "page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 20);
        Page<Goods> goods = goodsService.getMainGoodsPage(goodsSearchDto, pageable);

        model.addAttribute("goods", goods);
        model.addAttribute("goodsSearchDto", goodsSearchDto);
        model.addAttribute("maxPage", 5);

        return "goods/goodsIndex";
    }


    // 굿즈 등록 페이지
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
         return "redirect:/admin/goods/list";
    }


    // 굿즈 리스트 이동
    @GetMapping(value = {"/admin/goods/list", "/admin/goods/list/{page}"})
    public String goodsList(Model model, GoodsSearchDto goodsSearchDto,
                            @PathVariable("page") Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);

        Page<Goods> goods = goodsService.getAdminGoodsPage(goodsSearchDto, pageable);

        model.addAttribute("posts", goods );
        model.addAttribute("goodsFormDto", new GoodsFormDto());
        model.addAttribute("maxPage", 5);

        return "admin/goodsList";
    }


    // 굿즈 상세 페이지 이동
    @GetMapping(value = "/goods/goodsDtl/{goodId}")
    public String goodsDtl(Model model, @PathVariable(value ="goodId") Long goodId) {
        GoodsFormDto goodsFormDto = goodsService.getGoodsDtl(goodId);
        model.addAttribute("goods", goodsFormDto);
        return "goods/goodsDtl";
    }



    // 굿즈 수정페이지 보기
    @GetMapping(value = "/admin/goods/edit/{goodsId}")
    public String goodsModify(@PathVariable("goodsId") Long goodsId, Model model) {
        try {
            GoodsFormDto goodsFormDto = goodsService.getGoodsDtl(goodsId);
            model.addAttribute("goodsFormDto", goodsFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "굿즈(MD) 수정 중 오류가 발생했습니다.");

            model.addAttribute("goodsFormDto", new GoodsFormDto());
            return "admin/goodsModifyForm";
        }

        return "admin/goodsModifyForm";
    }


    // 굿즈 수정
    @PostMapping(value = "/admin/goods/edit/{goodsId}")
    public String goodsUpdate(@Valid GoodsFormDto goodsFormDto, Model model,
                              BindingResult bindingResult, @RequestParam("goodsImgFile") List<MultipartFile> goodsImgFileList,
                              @PathVariable("goodsId") Long goodsId) {

        if (bindingResult.hasErrors()) return "admin/goodsForm";

        GoodsFormDto getGoodsFormDto = goodsService.getGoodsDtl(goodsId);

        //상품등록 전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력값)
        if (goodsImgFileList.get(0).isEmpty() && goodsFormDto.getId() == null) {
            model.addAttribute("errorMessage",
                    "첫번째 상품 이미지는 필수 입력입니다.");
            model.addAttribute("goodsFormDto", getGoodsFormDto);
            return "admin/goodsModifyForm";
        }

        try {
            goodsService.updateGoods(goodsFormDto, goodsImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "굿즈(MD) 수정중 에러가 발생했습니다.");
            model.addAttribute("goodsFormDto", getGoodsFormDto);
            return "admin/goodsModifyForm";
        }

        return "redirect:/admin/goods/list";

    }

        //굿즈 삭제
    @DeleteMapping(value = "/admin/goods/delete/{goodsId}")
    public @ResponseBody ResponseEntity deleteGoods(@PathVariable(value = "goodsId") Long goodsId) {

        goodsService.deleteGoods(goodsId);

        return new ResponseEntity<Long>(goodsId, HttpStatus.OK);
    }

}

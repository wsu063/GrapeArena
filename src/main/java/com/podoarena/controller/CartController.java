package com.podoarena.controller;

import com.podoarena.dto.*;
import com.podoarena.entity.Cart;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.Member;
import com.podoarena.repository.CartRepository;
import com.podoarena.repository.GoodsCartRepository;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.CartService;
import com.podoarena.service.GoodsService;
import com.podoarena.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;
    private final GoodsService goodsService;


    // 현재 로그인한 멤버의 장바구니로 간다.
    @GetMapping(value = "/members/cart")
    public String cart(Principal principal, Model model) {
        if(principal == null) {
            return "index";
        } else {
            Member member = memberService.getMember(principal.getName());
            Cart cart = member.getCart();
            model.addAttribute("cart", cart);
            return "cart/cartList";
        }
    }

    //카트 추가
    @PostMapping(value = "/members/addCart")
    public @ResponseBody ResponseEntity addToCart(@Valid @RequestBody GoodsCartDto goodsCartDto,
                                                  BindingResult bindingResult, Model model, Principal principal) {
        //현재 선택한 굿즈를 선택한 수량과 함께 장바구니에 넣는다.

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();

        try {
            // Goods 엔티티의 정보를 가져옵니다.
            Goods goods = goodsService.getGoodsById(goodsCartDto.getGoodsId());
            // Goods 엔티티에서 goodsMaxAmount 속성을 가져옵니다.
            int goodsMaxAmount = goods.getGoodsMaxAmount();
            cartService.addToCart(goodsCartDto, principal.getName());
            return new ResponseEntity<String>("formData", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //카트 굿즈 삭제
    @DeleteMapping(value = "/goodsCart/delete/{goodsCartId}")
    public @ResponseBody ResponseEntity deleteGoodsCart(@PathVariable("goodsCartId") Long goodsCartId, Principal principal) {
        if (!cartService.validateGoodsCart(goodsCartId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteGoodsCart(goodsCartId);

        return new ResponseEntity<Long>(goodsCartId, HttpStatus.OK);
    }

    // 카트 굿즈 수량을 수정
    @PatchMapping(value = "/goodsCart/patch/{goodsCartId}")
    public @ResponseBody ResponseEntity updateGoodsCart(@PathVariable("goodsCartId") Long goodsCartId,
                                                        @RequestParam("count") int count,
                                                        Principal principal){

        GoodsCart goodsCart = cartService.getGoodsCart(goodsCartId);
        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateGoodsCart(goodsCartId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        if(!cartService.updateGoodsCartCount(goodsCartId, count)) {
            return new ResponseEntity<String>("최대 수량 이하로만 구매할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(goodsCartId, HttpStatus.OK);
    }

}










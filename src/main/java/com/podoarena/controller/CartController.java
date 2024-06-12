package com.podoarena.controller;

import com.podoarena.dto.*;
import com.podoarena.entity.Cart;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.Member;
import com.podoarena.repository.CartRepository;
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
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CartRepository cartRepository;
    private final GoodsService goodsService;


    @GetMapping(value = "/members/cart")
    public String cart(Principal principal, Model model) {
        List<CartListDto> cartListDtoList =
                cartService.getCartList(principal.getName());
        model.addAttribute("goodsCarts", cartListDtoList);

        return "cart/cartList";
    }


    //카트 추가
    @PostMapping(value = "/members/addCart")
    public @ResponseBody ResponseEntity addtoCart(@Valid @RequestBody CartDto cartDto,
                                                  BindingResult bindingResult, Model model, Principal principal) {
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
            Goods goods = goodsService.getGoodsById(cartDto.getGoodsId());

            // Goods 엔티티에서 goodsMaxAmount 속성을 가져옵니다.
            int goodsMaxAmount = goods.getGoodsMaxAmount();

            Long cartId = cartService.addToCart(cartDto, email, cartDto.getGoodsCount());
            model.addAttribute("member", memberService.getMember(email));
            model.addAttribute("memberFormDto", new MemberFormDto());
            model.addAttribute("goodsMaxAmount", goodsMaxAmount); // goodsMaxAmount를 모델에 추가합니다.

            return new ResponseEntity<String>("formData", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //카트 굿즈 삭제
    @DeleteMapping(value = "/goodsCart/{cartId}")
    public @ResponseBody ResponseEntity deleteGoodsCart(@PathVariable("cartId") Long cartId, Principal principal) {
//        if (!cartService.validateGoodsCart(cartId, principal.getName())) {
//            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }
        cartService.deleteCart(cartId);
//        cartService.deleteGoodsCart(goodsCartId); // 굿즈 삭제

        return new ResponseEntity<Long>(cartId, HttpStatus.OK);
    }

    // 카트 굿즈 수량을 수정
    @PatchMapping(value = "/goodsCart/{cartId}")
    public @ResponseBody ResponseEntity updateGoodsCart(@PathVariable("cartId") Long cartId,
                                                        @RequestParam("count") int count,
                                                        Principal principal){
        Cart cart = cartRepository.findById(cartId).
                orElseThrow(EntityNotFoundException::new);
        GoodsCart goodsCart = cart.getGoodsCarts().get(0);
        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateGoodsCart(cartId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }


        Long goodsCartId = goodsCart.getId();
        cartService.updateGoodsCartCount(goodsCartId, count);
        return new ResponseEntity<Long>(goodsCartId, HttpStatus.OK);
    }

    //카트에 담긴 굿즈 주문
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderGoodsCart(@RequestBody OrderDto orderDto, Principal principal) {
        List<OrderDto> orderDtoList = orderDto.getOrderDtoList();

        if (orderDtoList == null || orderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (OrderDto orderDtos : orderDtoList ) {
            if (!cartService.validateGoodsCart(orderDtos.getGoodsId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long ordersId = cartService.orderGoodsCart(new ArrayList<>(), principal.getName());
        return new ResponseEntity<Long>(ordersId, HttpStatus.OK);
    }



}










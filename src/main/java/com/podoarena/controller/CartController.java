package com.podoarena.controller;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Member;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepository;


    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid GoodsCartDto goodsCartDto, BindingResult bindingResult, Principal principal) {
        //유효성 검증에서 오류있는 경우
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            //오류 메세지들을 StringBuilder에 추가
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            //BAD_REQUEST 상태 코드와 함께 오류 메시지 반환
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        //현재 사용자 이메일 가져오기
        String email = principal.getName();
        Long goodsCartId;

        try {
            //카트에 항목 추가
            goodsCartId = cartService.addCart(goodsCartDto, memberRepository.findByEmail(email));
        } catch (Exception e) {
            //예외 발생 시 오류 메시지와 함게 BAD_REQUEST 상태 코드 변환
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        //성공적으로 추가된 항목 ID와 함께 OK 상태 코드 반환
        return new ResponseEntity<Long>(goodsCartId, HttpStatus.OK);
    }

    //사용자의 장바구니 목록을 조회
    public String orderHist(Principal principal, Model model) {
        List<GoodsCartDto> goodsCartList = cartService.getCartList(principal.getName()); //장바구니 목록 조회
    }

}

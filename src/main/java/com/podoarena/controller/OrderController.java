package com.podoarena.controller;

import com.podoarena.dto.OrderDto;
import com.podoarena.dto.OrderHistDto;
import com.podoarena.entity.GoodsCart;
import com.podoarena.repository.GoodsCartRepository;
import com.podoarena.service.CartService;
import com.podoarena.service.OrderService;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    // 선택한 굿즈카트를 주문페이지로 옮긴다
    @PostMapping(value = "/orders/order")
    public String orderPage(@RequestParam("goodsCartId") List<Long> goodsCartIdList,
                        Principal principal, Model model) {

        if (principal == null) {
            return "members/login";
        } else {
            List<GoodsCart> goodsCartList = new ArrayList<>();
            for(Long goodsCartId : goodsCartIdList) {
                GoodsCart goodsCart = cartService.getGoodsCart(goodsCartId);
                goodsCartList.add(goodsCart);
            }

            model.addAttribute(goodsCartList);

            return "orders/ordersIndex"; //성공시
        }
    }

    // 현재 결제페이지에 있는 굿즈카트들을 결제한다.
    @PostMapping(value = "/orders/orderNow")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
             BindingResult bindingResult,Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            //유효성 체크 후 에러결과 가져옴
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()); //에러메세지 합침
            }

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName(); //id에 해당하는 정보 가지고 옴(email)
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); //주문하기
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderId, HttpStatus.OK); //성공시
    }



    //주문내역
    @GetMapping(value = "/orders/orderHist")
    public String orderHist(Principal principal, Model model) {
        String email = principal.getName();

        List<GoodsCart> goodsCarts = orderService.getGoodsCartList(email);
        int totalPrice = goodsCarts.stream().mapToInt(cart -> cart.getGoodsCount() * cart.getGoods().getGoodsPrice()).sum();

        model.addAttribute("goodsCarts", goodsCarts);
        model.addAttribute("totalPrice", totalPrice);

        return "orders/ordersIndex";
    }

    // 주문 취소
    @PostMapping(value = "/orders/cancel/{orderId}")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
            Principal principal) {
        // 주문취소 권한이 있는지 확인(본인확인)
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }
        //주문취소
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK); //성공시
    }

    // 주문 삭제
    @DeleteMapping(value = "/orders/{orderId}")
    public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        //로그인한 사용자와 주문한 사용자가 같은지 확인
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<>("주문 삭제 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

}

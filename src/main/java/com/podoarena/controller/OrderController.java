package com.podoarena.controller;

import com.podoarena.dto.OrderDto;
import com.podoarena.dto.OrderHistDto;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/orders/ordersIndex")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
             BindingResult bindingResult,Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            //유효성 체크 후 에러결과 가져옴
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()); //에러메세지 합침
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName(); //id에 해당하는 정보 가지고 옴(email)
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); //주문하기
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK); //성공시
    }

    //주문내역
    @GetMapping(value = "/orders/ordersIndex")
    public String orderHist(@PathVariable("page")Optional<Integer> page,
                            Principal principal, Model model) {

        // 한 페이지당 5개의 데이터를 가지고 오도록 설정
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        // 서비스 호출
        Page<OrderHistDto> orderHistDtoList =
                orderService.getOrderList(principal.getName(), pageable);

        // 서비스에서 가져온 값들을 view단에 model을 이용해 전송
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("maxPage", 5); //하단에 보여줄 최대 페이지
        //model.addAttribute("page", pageable.getPageNumber()); //현재페이지
        return "orders/ordersIndex";
    }

    // 주문 취소
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId,
            Principal principal) {
        // 주문취소 권한이 있는지 확인(본인확인)
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }
        //주문취소
        orderService.cancelOrder(orderId);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK); //성공시
    }

    // 주문 삭제
    public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId
    , Principal principal) {
        //로그인한 사용자와 주문한 사용자가 같은지 확인
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 삭제 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }

        //주문삭제
        orderService.deleteOrder(orderId);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}

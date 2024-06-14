package com.podoarena.service;


import com.podoarena.dto.GoodsCartDto;
import com.podoarena.dto.OrderDto;
import com.podoarena.dto.OrderHistDto;
import com.podoarena.entity.*;
import com.podoarena.repository.*;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final GoodsCartRepository goodsCartRepository;
    private final CartService cartService;

    //주문하기
    public Long order(OrderDto orderDto, String email) {
        //1. 주문한 굿즈의 객체를 가져온다.
        for(Long goodsCartId : orderDto.getGoodsCartIds()) {
            GoodsCart goodsCart = cartService.getGoodsCart(goodsCartId);
            orderDto.getGoodsCartList().add(goodsCart);
        }
//
//        //2. 현재 로그인한 회원의 이메일을 이용해 member 엔티티를 가져온다
//        Member member = memberRepository.findByEmail(email);
//
//        //양방향 관계일때 save
//        List<OrderGoods> orderGoodsList = new ArrayList<>();
//        OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDto.getCount());
//        orderGoodsList.add(orderGoods);
//
//        Orders orders = Orders.createOrder(member, orderGoodsList);
//        orderRepository.save(orders); //insert

        return null;

    }

    @Transactional(readOnly = true)
    public List<GoodsCart> getGoodsCartList(String email) {
        Member member = memberRepository.findByEmail(email);
        return goodsCartRepository.findByMember(member);
    }

    //주문 목록 가져오기
    //@Transactional(readOnly = true)
//    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
//        // 유저 아이디를 이용해서 주문 목록 조회
//        List<Orders> ordersList = orderRepository.findOrders(email, pageable);
//
//        // 유저의 총 주문 개수를 구한다
//        Long totalCount = orderRepository.countOrder(email);
//
//        // 주문내역은 여러개이므로 리스티에 저장
//        List<OrderHistDto> orderHistDtos = new ArrayList<>();
//
//        // 주문 리스트 ordersList를 순회하면서 컨트롤러에 전달할 OrderHistDto 생성
//        for (Orders orders : ordersList) {
//            OrderHistDto orderHistDto = new OrderHistDto(orders);
//
//            List<OrderGoods> orderGoodsList = orders.getOrderGoodsList();
//
//            for (OrderGoods orderGoods : orderGoodsList) {
//
//
////                orderHistDto.addGoodsCartDto(goodsCartDto);
//            }
//
//            orderHistDtos.add(orderHistDto);
//        }
//
//        return new PageImpl<>(orderHistDtos, pageable, totalCount); //페이지 구현 객체를 생성하여 return
//    }

    //본인확인
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new); //주문내역

        Member savedMember = orders.getMember(); //주문한 사용자 찾기

        return curMember.getEmail().equals(savedMember.getEmail());
    }

    //주문 취소
    public void cancelOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new);
        //OrderStatus를 update -> entity 필드값 변경해주기
        orders.cancelOrder();
    }

    //주문 삭제
    public void deleteOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new);

        //delete
        orderRepository.delete(orders);
    }

    public Long orders(List<OrderDto> orderDtoList, String email) {
//        Member member = memberRepository.findByEmail(email);
//        List<OrderGoods> orderGoodsList = new ArrayList<>();
//
//        for (OrderDto orderDto : orderDtoList) {
//            Goods goods = goodsRepository.findById(orderDto.getGoodsId())
//                    .orElseThrow(EntityExistsException::new);
//
//            OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDto.getCount());
//            orderGoodsList.add(orderGoods);
//        }
//
//        Orders orders = Orders.createOrder(member, orderGoodsList);
//        orderRepository.save(orders);

        return null;
    }

    //카트 굿즈 주문
    public Long orderGoodsCart(List<GoodsCartDto> goodsCartDtoList, String email) {
        List<OrderDto> orderDtoList = new ArrayList<>();

//        //주문할 각 카트 굿즈
//        for (GoodsCartDto goodsCartDto : goodsCartDtoList) {
//            GoodsCart goodsCart = goodsCartRepository
//                    .findById(goodsCartDto.getGoodsCartId())
//                    .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아온다
//
//            OrderDto orderDto = new OrderDto();
//            orderDto.setGoodsId(goodsCart.getCart().getId()); // 굿즈 아이디 설정
//            orderDto.setCount(goodsCart.getGoodsCount()); // 수량 설정
//            orderDtoList.add(orderDto);
//        }
//
//        //주문 서비스 호출하여 주문 생성
//        Long orderId = orderService.orders(orderDtoList, email);
//        for (OrderDto orderDto : orderDtoList) {
//            GoodsCart goodsCart = goodsCartRepository
//                    .findById(orderDto.getGoodsId())
//                    .orElseThrow(EntityNotFoundException::new);
//            goodsCartRepository.delete(goodsCart);
//        }

        return null;
    }

    //카트에 담긴 굿즈 주문
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderGoodsCart(@RequestBody OrderDto orderDto, Principal principal) {
//        List<OrderDto> orderDtoList = orderDto.getOrderDtoList();
//
//        if (orderDtoList == null || orderDtoList.size() == 0) {
//            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
//        }
//
//        for (OrderDto orderDtos : orderDtoList ) {
//            if (!cartService.validateGoodsCart(orderDtos.getGoodsId(), principal.getName())){
//                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
//            }
//        }
//
//        Long ordersId = orderGoodsCart(new ArrayList<>(), principal.getName());
//        return new ResponseEntity<Long>(ordersId, HttpStatus.OK);
        return null;
    }

}

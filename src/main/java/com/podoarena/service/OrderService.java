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
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final GoodsCartRepository goodsCartRepository;
    private final CartService cartService;
    private final GoodsService goodsService;
    private final OrderGoodsRepository orderGoodsRepository;

    //주문하기. 하나라도 실패하면 전체 취소되어야한다.
    @Transactional
    public Long order(OrderDto orderDto, String email) {
        //현재 로그인한 회원의 이메일을 이용해 member 엔티티를 가져온다
        Member member = memberRepository.findByEmail(email);

        List<OrderGoods> orderGoodsList = new ArrayList<>();

        boolean buyNow = orderDto.getGoodsCartIds().isEmpty();

        //장바구니에서 결제로 이동했을시
        if(!buyNow) {
            //OrderGoodsList 구하고, 결제 완료된 goodsCart를 장바구니에서 삭제한다.
            for(Long goodsCartId : orderDto.getGoodsCartIds()) {
                GoodsCart goodsCart = cartService.getGoodsCart(goodsCartId);
                OrderGoods orderGoods = OrderGoods.createOrderGoods(goodsCart.getGoods(), goodsCart.getGoodsCount());
                orderGoodsList.add(orderGoods);
                cartService.deleteGoodsCart(goodsCartId);
            }
        } else {
            //굿즈 상세페이지에서 바로 구매로 이동했을시
            //OrderGoodsList 구한다.
            Goods goods = goodsService.getGoodsById(orderDto.getGoodsIds().get(0));
            int goodsCount = Math.toIntExact(orderDto.getGoodsCounts().get(0));
            OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, goodsCount);
            orderGoodsList.add(orderGoods);
        }
        // 주문결과를 만들고, 주문결과를 현재 멤버에 저장한다.
        Orders orders = Orders.createOrder(member, orderGoodsList);
        orderRepository.save(orders);
        member.getOrdersList().add(orders);

        return orders.getId();

    }

    @Transactional(readOnly = true)
    public List<GoodsCart> getGoodsCartList(String email) {
        Member member = memberRepository.findByEmail(email);
        return goodsCartRepository.findByMember(member);
    }

    //주문 목록 가져오기
    public List<Orders> getOrderHist(String email) {
        // 유저 아이디를 이용해서 주문 목록 조회
        List<Orders> ordersList = orderRepository.findOrders(email);

        return ordersList; //페이지 구현 객체를 생성하여 return
    }

    //본인확인
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new); //주문내역

        Member savedMember = orders.getMember(); //주문한 사용자 찾기

        return curMember.getEmail().equals(savedMember.getEmail());
    }

    //주문 취소
    public void cancelOrder(Long orderId, String email) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new);
        //OrderStatus를 update -> entity 필드값 변경해주기
        orders.cancelOrder();
        // 주문에 포함된 오더굿즈 삭제하기
        for (OrderGoods orderGoods: orders.getOrderGoodsList()) {
            orderGoodsRepository.delete(orderGoods);
        }
        //멤버에 포함된 orders객체 삭제하기
        Member member = memberRepository.findByEmail(email);
        member.getOrdersList().remove(orders);
        deleteOrder(orderId);
    }

    //주문 삭제
    public void deleteOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new);

        //delete
        orderRepository.delete(orders);
    }
}

package com.podoarena.service;

import com.podoarena.dto.OrderDto;
import com.podoarena.entity.Goods;
import com.podoarena.entity.Member;
import com.podoarena.entity.OrderGoods;
import com.podoarena.entity.Orders;
import com.podoarena.repository.GoodsImgRepository;
import com.podoarena.repository.GoodsRepository;
import com.podoarena.repository.MemberRepository;
import com.podoarena.repository.OrderRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final GoodsImgRepository goodsImgRepository;

    //주문하기
    public Long order(OrderDto orderDto, String email) {

        //1. 주문한 굿즈의 객체를 가져온다.
        Goods goods = goodsRepository.findById(orderDto.getGoodsId())
                                        .orElseThrow(EntityExistsException::new);

        //2. 현재 로그인한 회원의 이메일을 이용해 member 엔티티를 가져온다
        Member member = memberRepository.findByEmail(email);

        //양방향 관계일때 save
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDto.getCount());
        orderGoodsList.add(orderGoods);

        Orders orders = Orders.createOrder(member, orderGoodsList);
        orderRepository.save(orders); //insert

        return orders.getId();

    }

    //주문 목록 가져오기(OrderHistDto 필요해서 PASS)

    //본인확인
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityExistsException::new); //주문내역

        Member savedMember = orders.getMember(); //주문한 사용자 찾기

        //로그인한 사용자의 이메일과 주문한 사용자의 이메일이 같은지 최종 비교
        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            //같지 않으면
            return false;
        }

        return true;
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
        Member member = memberRepository.findByEmail(email);
        List<OrderGoods> orderGoodsList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Goods goods = goodsRepository.findById(orderDto.getGoodsId())
                    .orElseThrow(EntityExistsException::new);

            OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDto.getCount());
            orderGoodsList.add(orderGoods);
        }

        Orders orders = Orders.createOrder(member, orderGoodsList);
        orderRepository.save(orders);

        return orders.getId();
    }

}
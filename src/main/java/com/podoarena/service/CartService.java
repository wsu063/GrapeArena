package com.podoarena.service;

import com.podoarena.constant.RepImgYn;
import com.podoarena.dto.CartDto;
import com.podoarena.dto.CartListDto;
import com.podoarena.dto.GoodsCartDto;
import com.podoarena.dto.OrderDto;
import com.podoarena.entity.*;
import com.podoarena.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final GoodsCartRepository goodsCartRepository;
    private final OrderService orderService;
    private final GoodsImgRepository goodsImgRepository;

  //장바구니 추가
    public Long addToCart(CartDto cartDto, String email, int goodsCount) throws Exception {
        Member member = memberRepository.findByEmail(email);
        Goods goods = goodsRepository.findById(cartDto.getGoodsId())
                .orElseThrow(EntityNotFoundException::new);

        List<GoodsCart> goodsCartList = new ArrayList<>();
        GoodsCart goodsCart = GoodsCart.createGoodsCart(goods, member, goodsCount);
        goodsCartList.add(goodsCart);

        Cart cart = Cart.createCart(member, goodsCartList);

        memberRepository.save(member);

        cartRepository.save(cart);
        return goodsCart.getId();
    }
 //카트리스트 가져오기
    @Transactional(readOnly = true)
    public List<CartListDto> getCartList(String email) {
        List<Cart> cartList = cartRepository.findCart(email);

        List<CartListDto> cartListDtoList = new ArrayList<>();

        for (Cart cart : cartList) {
            CartListDto cartListDto = new CartListDto(cart);

            List<GoodsCart> goodsCartList = cart.getGoodsCarts();

            for (GoodsCart goodsCart : goodsCartList) {
                GoodsImg goodsImg = goodsImgRepository
                        .findByGoodsIdAndRepImgYn(goodsCart.getGoods().getId(), RepImgYn.Y);

                GoodsCartDto goodsCartDto =
                        new GoodsCartDto(goodsCart, goodsImg.getImgUrl());
                cartListDto.addGoodsCartDto(goodsCartDto);
            }

            cartListDtoList.add(cartListDto);
        }
        return cartListDtoList;
    }

    //장바구니(카트) 상품 추가 메소드
//    public Long addCart(GoodsCartDto goodsCartDto, Member member) {
//
//        //상품을 ID로 조회
//        Goods goods = goodsRepository.findById(goodsCartDto.getGoodsCartId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        //회원 장바구니(카트) 조회
//        Cart cart = cartRepository.findByMemberId(member.getId());
//        if (cart == null) {
//            cart = Cart.createCart(member);
//            cartRepository.save(cart);
//        }
//
//        //카트에 동일한 상품이 있는지 확인
//        GoodsCart savedGoodsCart = goodsCartRepository.findByGoodsId(cart.getId());
//
//        //같은 굿즈가 있으면 수량을 증가
//        if (savedGoodsCart != null) {
//            savedGoodsCart.setGoodsCount(goodsCartDto.getGoodsCount() + savedGoodsCart.getGoodsCount());
//        } else {
//            //같은 굿즈가 없음 새로운 카트 굿즈를 생성하여 저장
//            GoodsCart goodsCart = GoodsCart.createGoodsCart(goods,member, goodsCartDto.getGoodsCount());
//            goodsCartRepository.save(goodsCart);
//        }
//        return cart.getId(); //새 아이템의 아이디 변환
//    }


//        //회원의 장바구니를 조회
//        Cart cart = cartRepository.findByMemberId(member.getId());
//
//        if(cart == null) {
//            return goodsCartDtoList; //카트가 없으면 빈 리스트 반환
//        }
//
//        List<GoodsCart> getGoodsCartList = goodsCartRepository.findGoodsCartList(cart.getId());
//
//        for(GoodsCart goodsCart : getGoodsCartList) {
//            GoodsCartDto goodsCartDto = GoodsCartDto.of(goodsCart);
//            goodsCartDtoList.add(goodsCartDto);
//        }
//
//        return goodsCartDtoList;
//    }
//
    //카트 아이템의 소유자 검증
    public boolean validateGoodsCart(Long id, String email) {
        Member curMember = memberRepository.findByEmail(email); //현재 회원 정보
        GoodsCart goodsCart = goodsCartRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴

        Member savedMember = goodsCart.getCart().getMember(); //카트 아이템의 소유자

        //현재 회원과 카트 굿즈 소유자의 이메일이 같은지 확인
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false; //다르면 false 반환
        }
        return true; //같으면 ture 반환
    }

    // 카트 굿즈 수량 업데이트
    public void updateGoodsCartCount(Long goodsCartId, int count) {
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴

        goodsCart.setGoodsCount(count); //수량 업데이트
    }

    // 카트 굿즈 삭제
    public void deleteGoodsCart(Long goodsCartId) {
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴
        goodsCartRepository.delete(goodsCart); //카트 아이템 삭제
    }
//
//    //카트 굿즈 주문
//    public Long orderGoodsCart(List<GoodsCartDto> goodsCartDtoList, String email) {
//        List<OrderDto> orderDtoList = new ArrayList<>();
//
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
//
//        return orderId;
//    }

}

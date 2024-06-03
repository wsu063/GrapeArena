package com.podoarena.service;

import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.Cart;
import com.podoarena.entity.Goods;
import com.podoarena.entity.GoodsCart;
import com.podoarena.entity.Member;
import com.podoarena.repository.CartRepository;
import com.podoarena.repository.GoodsCartRepository;
import com.podoarena.repository.GoodsRepository;
import com.podoarena.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //장바구니(카트) 상품 추가 메소드
    public Long addCart(GoodsCartDto goodsCartDto, Member member) {

        //상품을 ID로 조회
        Goods goods = goodsRepository.findById(goodsCartDto.getGoodCartId())
                .orElseThrow(EntityNotFoundException::new);

        //회원 장바구니(카트) 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        //카트에 동일한 상품이 있는지 확인
        GoodsCart savedGoodsCart = goodsCartRepository.findByGoodsId(cart.getId());

        if (savedGoodsCart != null) {
            savedGoodsCart.setGoodsCount(goodsCartDto.getGoodsCount() + savedGoodsCart.getGoodsCount());
        } else {
            //없음 새로운 카트 굿즈를 생성하여 저장
            GoodsCart goodsCart = GoodsCart.createGoodsCart(goods,member, goodsCartDto.getGoodsCount());
            goodsCartRepository.save(goodsCart);
            return cart.getId();
        }
        return cart.getId();
    }

    //카트 목록 조회
//    public List<GoodsCartDto> getGoodsCartDtoList(String email) {
//        List<GoodsCartDto> GoodsCartDtoList = new ArrayList<>();
//
//        //이메일로 회원 조회
//        Member member = memberRepository.findByEmail(email);
//        Cart cart = cartRepository.findByMemberId(member.getId());
//
//        //회원의 장바구니를 조회
//        List<GoodsCartDto> goodsCartList = goodsRepository.findByCartId(cart.getId());
//
//        return goodsCartList;
//    }
}

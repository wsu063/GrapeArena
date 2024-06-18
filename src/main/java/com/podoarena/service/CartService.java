package com.podoarena.service;
import com.podoarena.dto.GoodsCartDto;
import com.podoarena.entity.*;
import com.podoarena.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    // Cart, GoodsCart Service
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final GoodsCartRepository goodsCartRepository;
    private final GoodsService goodsService;

    //장바구니 추가
    public Long addToCart(GoodsCartDto goodsCartDto, String email) {
        //선택한 물품 한종류를 여러개 추가한다.

        Member member = memberRepository.findByEmail(email);
        Cart cart = member.getCart();
        Goods goods = goodsService.getGoodsById(goodsCartDto.getGoodsId());

        // 카트에 동일한 상품이 있는지 확인
        GoodsCart savedGoodsCart = goodsCartRepository.findByGoodsIdAndCartId(goods.getId(),cart.getId());

        // 같은 굿즈가 있으면 수량을 증가
        if (savedGoodsCart != null) {
            int count = goodsCartDto.getGoodsCount() + savedGoodsCart.getGoodsCount();

            //만약 개인 최대 구매 수량을 넘지 않으면 그만큼 추가한다.
            //개인 최대 구매 수량을 넘으면 최대수량만큼만 카운트를 셋한다.
            savedGoodsCart.setGoodsCount(Math.min(count, goods.getGoodsMaxAmount()));
            return savedGoodsCart.getId();
        } else {
            //같은 굿즈가 없으면 새로운 카트 굿즈를 생성하여 저장
            GoodsCart goodsCart = GoodsCart.createGoodsCart(goods, member, goodsCartDto.getGoodsCount());
            goodsCartRepository.save(goodsCart);
            //추가된 굿즈카트를 현재 로그인한 멤버의 카트에 추가한다.
            goodsCart.setCart(cart);
            cart.getGoodsCarts().add(goodsCart);
            return goodsCart.getId();
        }
    }

    //카트 아이템의 소유자 검증
    public boolean validateGoodsCart(Long goodsCartId, String email) {
        Member curMember = memberRepository.findByEmail(email); //현재 회원 정보
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴

        Member savedMember = goodsCart.getCart().getMember(); //카트 아이템의 소유자

        //현재 회원과 카트 굿즈 소유자의 이메일이 같은지 확인
        //같으면 ture 반환
        return StringUtils.equals(curMember.getEmail(), savedMember.getEmail()); //다르면 false 반환

    }

    // 카트 굿즈 수량 업데이트
    public boolean updateGoodsCartCount(Long goodsCartId, int count) {
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴
        int max = goodsCart.getGoods().getGoodsMaxAmount();

        if(count <= max)
        {
            goodsCart.setGoodsCount(count); //수량 업데이트
            return true;
        } else {
            goodsCart.setGoodsCount(max);
            return false;
        }

    }

    // 카트 굿즈 삭제
    public void deleteGoodsCart(Long goodsCartId) {
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴
        //카트에 있는 굿즈카트 삭제
        Cart cart = goodsCart.getCart();
        cart.getGoodsCarts().remove(goodsCart);
        //카트 아이템 삭제
        goodsCartRepository.delete(goodsCart);

    }

    public GoodsCart getGoodsCart(Long goodsCartId) {
        return goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new);
    }


}

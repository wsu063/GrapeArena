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

        //같은 굿즈가 있으면 수량을 증가
        if (savedGoodsCart != null) {
            savedGoodsCart.setGoodsCount(goodsCartDto.getGoodsCount() + savedGoodsCart.getGoodsCount());
        } else {
            //같은 굿즈가 없음 새로운 카트 굿즈를 생성하여 저장
            GoodsCart goodsCart = GoodsCart.createGoodsCart(goods,member, goodsCartDto.getGoodsCount());
            goodsCartRepository.save(goodsCart);
        }
        return cart.getId(); //새 아이템의 아이디 변환
    }

    //카트 목록 가져옴
    @Transactional(readOnly = true)
    public List<GoodsCartDto> getGoodsCartDtoList(String email) {
        List<GoodsCartDto> GoodsCartDtoList = new ArrayList<>(); //빈 리스트 생성

        //이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        //회원의 장바구니를 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null) {
            return GoodsCartDtoList; //카트가 없으면 빈 리스트 반환
        }

        //카트굿즈 리스트를 가져와서 반환
        GoodsCartDtoList =  goodsCartRepository.findGoodsCartDtoList(cart.getId());
        return GoodsCartDtoList;
    }

    //카트 아이템의 소유자 검증
    public boolean validateGoodsCar(Long id, String email) {
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
    public void  updateGoodsCartCount(Long goodsCartId, int count) {
        GoodsCart goodsCart = goodsCartRepository.findById(goodsCartId)
                .orElseThrow(EntityNotFoundException::new); //카트 굿즈를 아이디로 찾아옴

        goodsCart.setGoodsCount(count); //수량 업데이트
    }
}

package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsCart> goodsCarts = new ArrayList<>();

    //추가
    public void addCart(GoodsCart goodsCart) {
        goodsCarts.add(goodsCart);
        goodsCart.setCart(this);
    }
    //생성
    public static Cart createCart(Member member, List<GoodsCart> goodsCartList) {
        Cart cart = new Cart();
        cart.setMember(member);

        for(GoodsCart goodsCart : goodsCartList) {
            cart.addCart(goodsCart);
        }
        return cart;
    }
}

package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="goods_cart")
@Getter
@Setter
@ToString
public class GoodsCart {
    @Id
    @Column(name = "goods_cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int goodsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //생성
    public static GoodsCart createGoodsCart(Goods goods, Member member, int goodsCount) {
        GoodsCart goodsCart = new GoodsCart();
        goodsCart.setGoods(goods);
        goodsCart.setCart(member.getCart());
        goodsCart.setGoodsCount(goodsCount);

        return goodsCart;
    }

}

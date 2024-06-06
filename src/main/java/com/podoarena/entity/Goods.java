package com.podoarena.entity;


import com.podoarena.constant.SellStatus;
import com.podoarena.dto.GoodsFormDto;
import com.podoarena.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "goods")
@Getter
@Setter
public class Goods extends BaseEntity {
    @Id
    @Column(name = "goods_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goodsName; // 이름
    private int goodsPrice; // 가격
    private int goodsStock; // 재고
    private int goodsMaxAmount; // 수량
    private String sort; //분류

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus; // 판매상태

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsImg> goodsImgs;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsCart> goodsCarts;


    //lecture엔티티 수정
//    public void updateLecture(LectureFormDto lectureFormDto) {
//        this.title = lectureFormDto.getTitle();
//        this.content = lectureFormDto.getContent();
//        this.subject = lectureFormDto.getSubject();
//    }

    //재고 감소
    public void removeStock(int goodsStock) {
        int restStock = this.goodsStock - goodsStock; //남은수량 = 굿즈 재고수량 - 주문수량

        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다." + "현재 재고수량: " + this.goodsStock );
        }

        this.goodsStock = restStock; //남은 재고수량 반영
    }

    //재고 증가
    public void addStock(int goodsStock) { this.goodsStock += goodsStock; }

    public void updateGoods(GoodsFormDto goodsFormDto) {
        this.sellStatus = goodsFormDto.getSellStatus();
        this.goodsName = goodsFormDto.getGoodsName();
        this.goodsPrice = goodsFormDto.getGoodsPrice();
        this.goodsMaxAmount = goodsFormDto.getGoodsMaxAmount();
        this.goodsStock = goodsFormDto.getGoodsStock();
    }
}

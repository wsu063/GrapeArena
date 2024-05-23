package com.podoarena.entity;

import com.podoarena.constant.SellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "goods")
@Getter
@Setter
@ToString
public class Goods extends BaseEntity {
    @Id
    @Column(name = "goods_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private String item;
    private int goodsStock;

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

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
}

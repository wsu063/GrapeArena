package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_goods")
@Getter
@Setter
@ToString
public class OrderGoods extends BaseEntity {
    @Id
    @Column(name = "order_goods_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderPrice;
    private int orderCount;
}

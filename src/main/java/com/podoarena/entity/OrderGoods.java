package com.podoarena.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.query.Order;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    private int orderPrice;
    
    private int orderCount;


    //주문할 굿즈와 주문수량을 통해 orderGoods 객체 만듬
    public static OrderGoods createOrderGoods(Goods goods, int orderCount) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoods(goods);
        orderGoods.setOrderCount(orderCount);
        orderGoods.setOrderPrice(goods.getGoodsPrice() * orderCount);

        goods.removeStock(orderCount); //재고감소

        return orderGoods;
    }

    public int getTotalPrice() {
        return orderPrice * orderCount;
    }

    //재고 원래대로
    public void cancel() {this.getGoods().addStock(orderCount);}
}

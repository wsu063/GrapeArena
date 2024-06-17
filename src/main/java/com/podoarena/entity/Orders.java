package com.podoarena.entity;

import com.podoarena.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Orders extends BaseEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate; //주문날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL,
    orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderGoods> orderGoodsList =  new ArrayList<>();

    public void addOrderGoods(OrderGoods orderGoods) {
        this.orderGoodsList.add(orderGoods);
        orderGoods.setOrders(this);
    }

    //orders 객체를 생성
    public static Orders createOrder(Member member, List<OrderGoods> orderGoodsList) {
        Orders orders = new Orders();
        orders.setMember(member);

        for (OrderGoods orderGoods : orderGoodsList) {
            orders.addOrderGoods(orderGoods);
        }

        orders.setOrderStatus(OrderStatus.ORDER);
        orders.setOrderDate(LocalDateTime.now());

        return orders;
    }

    //총 주문금액
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderGoods orderGoods : orderGoodsList) {
            totalPrice += orderGoods.getTotalPrice();
        }
        return totalPrice;
    }

    //주문 취소
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        //재고를 원래대로 돌려 놓는다.
        for (OrderGoods orderGoods: orderGoodsList) {
            orderGoods.cancel();
        }
    }
}

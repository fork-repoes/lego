package com.geekhalo.lego.query;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Entity
@Table(name = "query_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "price")
    private int price;
}

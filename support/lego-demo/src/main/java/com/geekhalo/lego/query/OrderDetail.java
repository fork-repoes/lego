package com.geekhalo.lego.query;

import lombok.Data;

import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class OrderDetail {
    private Order order;
    @JoinItemByOrder(keyFromSourceData = "#{order.id}")
    private List<OrderItem> orderItems;

    public OrderDetail(Order order){
        this.order = order;
    }
}

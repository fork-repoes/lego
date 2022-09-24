package com.geekhalo.lego.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderItemQueryRepository extends JpaRepository<OrderItem, Long>{

    List<OrderItem> getByOrderIdIn(List<Long> orderIds);
}

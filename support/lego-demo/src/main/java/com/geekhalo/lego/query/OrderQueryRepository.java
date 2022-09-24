package com.geekhalo.lego.query;

import com.geekhalo.lego.core.singlequery.QueryObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderQueryRepository
        extends JpaRepository<Order, Long>,
        QueryObjectRepository<Order>{

    List<Order> getByUserIdAndStatus(Long id, OrderStatus paid);
}

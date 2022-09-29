package com.geekhalo.lego.query;

import com.geekhalo.lego.core.query.QueryRepository;
import com.geekhalo.lego.core.singlequery.QueryObjectRepository;
import org.assertj.core.util.Lists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderQueryRepository
        extends JpaRepository<Order, Long>,
        QueryRepository<Order, Long> {


    List<Order> getByUserIdAndStatus(Long id, OrderStatus paid);
}

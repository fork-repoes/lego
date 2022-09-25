package com.geekhalo.lego.query;

import com.geekhalo.lego.core.query.QueryServiceDefinition;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@QueryServiceDefinition(domainClass = Order.class,
        repositoryClass = OrderQueryRepository.class)
public interface OrderQueryServiceProxy extends OrderQueryService {
}

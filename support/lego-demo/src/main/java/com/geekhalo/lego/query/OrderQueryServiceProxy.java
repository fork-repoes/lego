package com.geekhalo.lego.query;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import org.springframework.validation.annotation.Validated;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@QueryServiceDefinition(domainClass = Order.class,
        repositoryClass = OrderQueryRepository.class)
@Validated
public interface OrderQueryServiceProxy extends OrderQueryService {
}

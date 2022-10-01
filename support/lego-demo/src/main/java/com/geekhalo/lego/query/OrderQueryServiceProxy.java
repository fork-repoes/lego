package com.geekhalo.lego.query;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.lego.core.singlequery.Page;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@QueryServiceDefinition(domainClass = Order.class,
        repositoryClass = OrderQueryRepository.class)
@Validated
public interface OrderQueryServiceProxy extends CustomOrderQueryService, OrderQueryService {
    OrderDetail getById(@Valid @NotNull(message = "订单号不能为null") Long id);

    Page<OrderDetail> pageByUserId(@Valid @NotNull(message = "查询参数不能为 null") PageByUserId query);

    List<OrderDetail> getByUserId(@Valid @NotNull(message = "查询参数不能为 null") GetByUserId getByUserId);

    Long countByUser(@Valid @NotNull(message = "查询参数不能为 null") CountByUserId countByUserId);

    List<OrderDetail> getPaidByUserId(Long id);
}

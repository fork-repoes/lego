package com.geekhalo.lego.query;

import com.geekhalo.lego.core.singlequery.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderQueryService extends CustomOrderQueryService{

    OrderDetail getById(@Valid @NotNull(message = "订单号不能为null") Long id);

    Page<OrderDetail> pageByUserId(@Valid @NotNull(message = "查询参数不能为 null") PageByUserId query);

    List<OrderDetail> getByUserId(@Valid @NotNull(message = "查询参数不能为 null") GetByUserId getByUserId);

    Long countByUser(@Valid @NotNull(message = "查询参数不能为 null") CountByUserId countByUserId);

}

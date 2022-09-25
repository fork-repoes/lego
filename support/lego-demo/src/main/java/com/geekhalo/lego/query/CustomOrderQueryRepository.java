package com.geekhalo.lego.query;

import java.util.List;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CustomOrderQueryRepository {
    Order getByIds(List<Long> ids);
}

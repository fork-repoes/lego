package com.geekhalo.lego.core.joininmemory.support;

import lombok.Data;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class OrderDetail {
    private final Order order;
    private User user;
}

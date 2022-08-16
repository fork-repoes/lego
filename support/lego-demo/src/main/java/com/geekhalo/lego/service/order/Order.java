package com.geekhalo.lego.service.order;

import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Builder
@Data
public class Order {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;

}

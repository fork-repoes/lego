package com.geekhalo.lego.wide;

import com.geekhalo.lego.annotation.wide.BindFrom;
import lombok.Data;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Data
public class WideOrderItem{
    @BindFrom(sourceClass = OrderItem.class, field = "id")
    private Long orderId;

    @BindFrom(sourceClass = OrderItem.class, field = "status")
    private Integer orderStatus;

    @BindFrom(sourceClass = User.class, field = "id")
    private Long userId;

    @BindFrom(sourceClass = User.class, field = "name")
    private String userName;

    @BindFrom(sourceClass = Product.class, field = "id")
    private Long productId;

    @BindFrom(sourceClass = Product.class, field = "name")
    private String productName;
}

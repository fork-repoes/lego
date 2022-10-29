package com.geekhalo.lego.core.wide;

import com.geekhalo.lego.annotation.wide.BindFrom;
import lombok.Data;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Data
public class WideOrderItem implements Wide {
    @BindFrom(sourceClass = OrderItem.class, field = "id")
    private Long orderId;

    @BindFrom(sourceClass = OrderItem.class, field = "status")
    private Integer orderStatus;

    @BindFrom(sourceClass = OrderItem.class, field = "userId")
    private Long userId;

    @BindFrom(sourceClass = User.class, field = "name")
    private String userName;

    @BindFrom(sourceClass = OrderItem.class, field = "productId")
    private Long productId;

    @BindFrom(sourceClass = Product.class, field = "name")
    private String productName;

    @Override
    public boolean isValidate() {
        return true;
    }
}

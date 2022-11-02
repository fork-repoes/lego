package com.geekhalo.lego.core.wide;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Data
public class OrderItem implements WideItemData<WideOrderItemType, Long> {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer status;
    private Date updateDate;

    @Override
    public WideOrderItemType getItemType() {
        return WideOrderItemType.ORDER_ITEM;
    }

    @Override
    public Long getKey() {
        return id;
    }
}

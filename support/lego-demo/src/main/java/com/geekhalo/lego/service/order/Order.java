package com.geekhalo.lego.service.order;

import com.geekhalo.lego.core.wide.WideItemData;
import com.geekhalo.lego.wide.WideOrderType;
import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Builder
@Data
public class Order implements WideItemData<WideOrderType, Long> {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.ORDER;
    }

    @Override
    public Long getKey() {
        return id;
    }
}

package com.geekhalo.lego.service.address;

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
public class Address implements WideItemData<WideOrderType, Long> {
    private Long id;
    private Long userId;
    private String detail;

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.ADDRESS;
    }

    @Override
    public Long getKey() {
        return id;
    }
}

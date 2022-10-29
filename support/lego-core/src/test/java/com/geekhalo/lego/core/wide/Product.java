package com.geekhalo.lego.core.wide;

import lombok.Data;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class Product implements WideItemData<WideOrderItemType> {
    private Long id;
    private String name;

    @Override
    public WideOrderItemType getItemType() {
        return WideOrderItemType.PRODUCT;
    }
}

package com.geekhalo.lego.core.wide;

import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class User implements WideItemData<WideOrderItemType, Long> {
    private Long id;

    private String name;

    private Date updateDate;

    @Override
    public WideOrderItemType getItemType() {
        return WideOrderItemType.USER;
    }

    @Override
    public Long getKey() {
        return id;
    }
}

package com.geekhalo.lego.service.user;

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
public class User implements WideItemData<WideOrderType, Long> {
    private Long id;
    private String name;

    public boolean isEnable() {
        return true;
    }

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.USER;
    }

    @Override
    public Long getKey() {
        return id;
    }
}

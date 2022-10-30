package com.geekhalo.lego.core.wide;

import lombok.*;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Setter(AccessLevel.PROTECTED)
public abstract class WideIndexContext<ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>> {
    private ITEM_TYPE type;
}

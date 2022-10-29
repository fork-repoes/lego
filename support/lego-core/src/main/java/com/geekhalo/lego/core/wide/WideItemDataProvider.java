package com.geekhalo.lego.core.wide;

import com.geekhalo.lego.core.SmartComponent;

/**
 * Created by taoli on 2022/10/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideItemDataProvider<
        TYPE extends Enum<TYPE> & WideItemTypes<TYPE>,
        KEY,
        ITEM extends WideItemData<TYPE>>
    extends SmartComponent<TYPE> {

    ITEM apply(KEY key);
}

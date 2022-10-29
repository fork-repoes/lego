package com.geekhalo.lego.core.wide;

import com.geekhalo.lego.core.SmartComponent;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideItemUpdater<
        TYPE extends Enum<TYPE> & WideItemTypes<TYPE>,
        ITEM extends WideItemData<TYPE>,
        WIDE extends Wide> extends SmartComponent<TYPE> {
    void updateItem(ITEM item, WideCommandRepository<WIDE> repository);
}

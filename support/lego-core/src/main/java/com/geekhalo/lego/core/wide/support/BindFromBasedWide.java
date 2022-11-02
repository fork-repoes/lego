package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.Wide;
import com.geekhalo.lego.core.wide.WideIndexCompareContext;
import com.geekhalo.lego.core.wide.WideIndexSingleUpdateContext;
import com.geekhalo.lego.core.wide.WideItemType;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class BindFromBasedWide<ID, ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>>
        implements Wide<ID, ITEM_TYPE> {
    @Override
    public boolean isSameWithItem(WideIndexCompareContext<ITEM_TYPE> context) {
        return context.getWideWrapper().isSameWithItem(context.getItemData());
    }

    @Override
    public void updateByItem(WideIndexSingleUpdateContext<ITEM_TYPE> context) {
        context.getWideWrapper().updateItem(context.getItemData());
    }
}

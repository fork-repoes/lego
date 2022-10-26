package com.geekhalo.lego.core.wide;

import java.util.List;
/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideItemUpdater<T> {
    void updateItem(List<WideObjectWrapper> wideObjects);
}

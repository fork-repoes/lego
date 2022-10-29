package com.geekhalo.lego.core.wide;

import java.util.Collections;
import java.util.List;
/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideItemBinder<W extends Wide> {
    default void bindFor(WideWrapper<W> wideWrapper){
        if (wideWrapper == null){
            return;
        }
        bindFor(Collections.singletonList(wideWrapper));
    }

    void bindFor(List<WideWrapper<W>> wideWrappers);
}

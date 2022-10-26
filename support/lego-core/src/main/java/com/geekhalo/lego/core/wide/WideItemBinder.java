package com.geekhalo.lego.core.wide;

import java.util.Collections;
import java.util.List;
/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideItemBinder<W> {
    default void bindFor(W wide){
        if (wide == null){
            return;
        }
        bindFor(Collections.singletonList(wide));
    }

    void bindFor(List<W> wide);
}

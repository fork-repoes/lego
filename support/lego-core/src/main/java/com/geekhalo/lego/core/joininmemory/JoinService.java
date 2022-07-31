package com.geekhalo.lego.core.joininmemory;

import java.util.List;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface JoinService<T> {
    /**
     * 执行内存 Join
     * @param t
     */
    void joinInMemory(List<T> t);
}

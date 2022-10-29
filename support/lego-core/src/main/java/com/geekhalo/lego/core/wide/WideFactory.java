package com.geekhalo.lego.core.wide;

/**
 * Created by taoli on 2022/10/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideFactory<M, W extends Wide> {
    W create(M masterData);
}

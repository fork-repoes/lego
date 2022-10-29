package com.geekhalo.lego.core.wide;

/**
 * Created by taoli on 2022/10/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WidePatrolService<M, W extends Wide> {

    void onMainChange(M main);

    <I> void onItemChange(I item);
}

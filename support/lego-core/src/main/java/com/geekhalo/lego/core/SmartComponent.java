package com.geekhalo.lego.core;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SmartComponent<D> {
    boolean support(D d);
}

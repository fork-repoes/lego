package com.geekhalo.lego.core.spliter.support.spring.invoker;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 拆分执行器，主要是对方法调用的封装
 */
public interface SplitInvoker {
    Object invoke(Object target, Object[] args);
}

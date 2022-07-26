package com.geekhalo.lego.core.spliter.service;

import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 *
 * 参数拆分器，将入参按照一定规则进行拆分
 * @param <P> 入参类型
 */
public interface ParamSplitter<P> {

    List<P> split(P param, int maxSize);
}

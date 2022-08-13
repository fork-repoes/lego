package com.geekhalo.lego.core.spliter.support.executor;


import com.geekhalo.lego.core.spliter.MethodExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 顺序执行器，依次执行
 */
public class SerialMethodExecutor
        extends AbstractMethodExecutor
        implements MethodExecutor {

    @Override
    protected <R, P> List<R> doExecute(Function<P, R> function, List<P> ps) {
        return ps.stream()
                .map(p -> function.apply(p))
                .collect(Collectors.toList());
    }
}

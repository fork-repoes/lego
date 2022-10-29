package com.geekhalo.lego.core.wide;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/10/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@FunctionalInterface
public interface WideMasterDataProvider<T, R> extends Function<List<T>, List<R>> {
}

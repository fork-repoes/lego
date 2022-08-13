package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import java.util.function.Function;

/**
 * Created by taoli on 2022/8/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@FunctionalInterface
public interface HSSFValueSupplier<T, R> extends Function<T, R> {
}

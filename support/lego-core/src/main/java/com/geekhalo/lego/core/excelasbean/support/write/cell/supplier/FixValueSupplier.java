package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class FixValueSupplier implements HSSFValueSupplier {
    private final Object value;

    public FixValueSupplier(Object value) {
        this.value = value;
    }

    @Override
    public Object apply(Object o) {
        return value;
    }
}

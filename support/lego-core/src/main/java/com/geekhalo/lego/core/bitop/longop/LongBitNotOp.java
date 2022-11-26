package com.geekhalo.lego.core.bitop.longop;

import com.geekhalo.lego.core.bitop.intop.IntBitOp;
import com.geekhalo.lego.core.bitop.intop.IntMaskOp;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class LongBitNotOp implements LongBitOp {
    private final LongBitOp longBitOp;

    public LongBitNotOp(LongBitOp longBitOp) {
        this.longBitOp = longBitOp;
    }

    @Override
    public boolean match(long value) {
        return !this.longBitOp.match(value);
    }

    @Override
    public String toSqlFilter(String fieldName) {

        LongMaskOp longMaskOp = (LongMaskOp) longBitOp;
        return new StringBuilder()
                .append("(")
                .append(fieldName)
                .append(" & ")
                .append(longMaskOp.getMask())
                .append(")")
                .append("<>")
                .append(longMaskOp.getMask())
                .toString();
    }
}
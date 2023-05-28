package com.geekhalo.lego.core.bitop.longop;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class LongBitAndOp implements LongBitOp {
    private final LongBitOp[] longBitOps;

    LongBitAndOp(LongBitOp... longBitOps) {
        this.longBitOps = longBitOps;
    }

    @Override
    public boolean match(long value) {
        if (this.longBitOps == null || this.longBitOps.length == 0){
            return true;
        }
        return Stream.of(longBitOps)
                .allMatch(longMaskOp -> longMaskOp.match(value));
    }

    @Override
    public String toSqlFilter(String fieldName) {
        if (this.longBitOps == null || this.longBitOps.length == 0){
            return "";
        }
        return Stream.of(longBitOps)
                .map(intBitOp -> intBitOp.toSqlFilter(fieldName))
                .collect(Collectors.joining(" and ","(",")"));
    }
}
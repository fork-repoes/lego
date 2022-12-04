package com.geekhalo.lego.core.bitop.longop;

import com.geekhalo.lego.core.bitop.intop.IntBitAndOp;
import com.geekhalo.lego.core.bitop.intop.IntBitNotOp;
import com.geekhalo.lego.core.bitop.intop.IntBitOrOp;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface LongBitOp {
    boolean match(long value);

    String toSqlFilter(String fieldName);

    default LongBitOp or(LongBitOp other){
        return new LongBitOrOp(this, other);
    }

    default LongBitOp and(LongBitOp other){
        return new LongBitAndOp(this, other);
    }

    default LongBitOp not(){
        return new LongBitNotOp(this);
    }
}
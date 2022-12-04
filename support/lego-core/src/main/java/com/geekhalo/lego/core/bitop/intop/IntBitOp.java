package com.geekhalo.lego.core.bitop.intop;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface IntBitOp {
    boolean match(int value);

    String toSqlFilter(String fieldName);

    default IntBitOp or(IntBitOp other){
        return new IntBitOrOp(this, other);
    }

    default IntBitOp and(IntBitOp other){
        return new IntBitAndOp(this, other);
    }

    default IntBitOp not(){
        return new IntBitNotOp(this);
    }
}
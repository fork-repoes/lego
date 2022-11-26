package com.geekhalo.lego.core.bitop.tips;

import com.geekhalo.lego.core.bitop.intop.IntMaskOp;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class MessageTips {
    private static final IntMaskOp SYSTEM = IntMaskOp.MASK_1;
    private static final IntMaskOp LIKE = IntMaskOp.MASK_2;

    private int type = 0;

    public void setSystem(boolean tutu){
        this.type = SYSTEM.set(this.type, tutu);
    }

    public boolean isSystem(){
        return SYSTEM.isSet(this.type);
    }

    public void setLike(boolean gaotu){
        this.type = LIKE.set(this.type, gaotu);
    }

    public boolean isLike(){
        return LIKE.isSet(this.type);
    }

    public String filterByLike(String filed){
        return LIKE.toSqlFilter(filed);
    }

    public String filterBySystem(String filed){
        return SYSTEM.toSqlFilter(filed);
    }

    public String filterBySystemOrLike(String field){
        return SYSTEM.or(LIKE).toSqlFilter(field);
    }

    public String filterBySystemAndLike(String field){
        return SYSTEM.and(LIKE).toSqlFilter(field);
    }

    public String filterByNotSystem(String field){
        return SYSTEM.not().toSqlFilter(field);
    }
}

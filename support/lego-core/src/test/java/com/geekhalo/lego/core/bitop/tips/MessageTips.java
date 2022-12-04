package com.geekhalo.lego.core.bitop.tips;

import com.geekhalo.lego.core.bitop.longop.LongMaskOp;

/**
 * Created by taoli on 2022/11/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class MessageTips {
    private static final LongMaskOp SYSTEM = LongMaskOp.MASK_1;
    private static final LongMaskOp LIKE = LongMaskOp.MASK_2;
    private static final LongMaskOp FOLLOW = LongMaskOp.MASK_3;

    private Long userId;

    private long messageTips = 0L;

    public void setSystem(){
        this.messageTips = SYSTEM.set(this.messageTips);
    }

    public void cleanSystem(){
        this.messageTips = SYSTEM.unset(this.messageTips);
    }

    public boolean isHasSystem(){
        return SYSTEM.match(this.messageTips);
    }

    public void setLike(){
        this.messageTips = LIKE.set(this.messageTips);
    }

    public void cleanLike(){
        this.messageTips = LIKE.unset(this.messageTips);
    }

    public boolean isHasLike(){
        return LIKE.match(this.messageTips);
    }

    public void setFollow(){
        this.messageTips = FOLLOW.set(this.messageTips);
    }

    public void cleanFollow(){
        this.messageTips = FOLLOW.unset(this.messageTips);
    }

    public boolean isHasFollow(){
        return FOLLOW.match(this.messageTips);
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

package com.geekhalo.lego.faultrecovery.smart;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.geekhalo.lego.core.faultrecovery.smart.ActionType;

/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

public class ActionContext {
    private static final TransmittableThreadLocal<ActionType> ACTION_TYPE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(ActionType actionType){
        ACTION_TYPE_THREAD_LOCAL.set(actionType);
    }

    public static ActionType get(){
        return ACTION_TYPE_THREAD_LOCAL.get();
    }

    public static void clear(){
        ACTION_TYPE_THREAD_LOCAL.remove();
    }
}

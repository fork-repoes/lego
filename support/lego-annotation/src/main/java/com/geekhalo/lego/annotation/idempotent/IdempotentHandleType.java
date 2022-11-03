package com.geekhalo.lego.annotation.idempotent;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public enum IdempotentHandleType {
    ERROR, // 抛出异常
    RESULT; // 返回执行结果
}

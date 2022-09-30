package com.geekhalo.lego.core.query;

import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * QueryServiceMethod 丢失异常；
 */
@Data
@ToString(callSuper = true)
public class QueryServiceMethodLostException extends RuntimeException{
    private Set<Method> lostMethods;
    public QueryServiceMethodLostException(Set<Method> lostMethods) {
        super();
        this.lostMethods = lostMethods;
    }

    public QueryServiceMethodLostException(String message) {
        super(message);
    }

    public QueryServiceMethodLostException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryServiceMethodLostException(Throwable cause) {
        super(cause);
    }

    protected QueryServiceMethodLostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

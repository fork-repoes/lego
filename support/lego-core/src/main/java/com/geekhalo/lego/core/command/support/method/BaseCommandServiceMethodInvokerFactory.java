package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.AggRoot;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.BiFunction;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Getter(AccessLevel.PROTECTED)
abstract class BaseCommandServiceMethodInvokerFactory {
    private final Class<? extends AggRoot> aggClass;
    private final Class idClass;

    protected BaseCommandServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass, Class idClass) {
        this.aggClass = aggClass;
        this.idClass = idClass;
    }

    protected BiFunction createResultConverter(Class returnType){
        if (Void.class.equals(returnType) || Void.TYPE.equals(returnType)){
            return (agg, context) -> null;
        }

        if (this.aggClass.isAssignableFrom(returnType)){
            return (agg, context) -> agg;
        }
        if (this.idClass.equals(returnType)){
            return (agg, context) -> ((AggRoot)agg).getId();
        }
        if (Boolean.class.isAssignableFrom(returnType) || Boolean.TYPE.isAssignableFrom(returnType)){
            return (agg, context) -> true;
        }
        return (agg, context) -> null;
    }

}

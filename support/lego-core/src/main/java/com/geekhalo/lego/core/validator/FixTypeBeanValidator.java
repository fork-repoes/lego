package com.geekhalo.lego.core.validator;

import java.lang.reflect.ParameterizedType;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class FixTypeBeanValidator<A> implements BeanValidator<A>{
    private final Class<A> type;

    protected FixTypeBeanValidator(){
        Class<A> type = (Class<A>)((ParameterizedType)getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.type = type;
    }

    protected FixTypeBeanValidator(Class<A> type) {
        this.type = type;
    }

    @Override
    public final boolean support(Object a) {
        return this.type.isInstance(a);
    }

}

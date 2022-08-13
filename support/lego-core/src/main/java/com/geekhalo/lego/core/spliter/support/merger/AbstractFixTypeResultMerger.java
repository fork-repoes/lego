package com.geekhalo.lego.core.spliter.support.merger;

import com.geekhalo.lego.core.spliter.SmartResultMerger;
import com.google.common.reflect.TypeToken;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 从泛型中获取类型，用于进行 support
 */
abstract class AbstractFixTypeResultMerger<R>
        extends AbstractResultMerger<R>
        implements SmartResultMerger<R> {
    private final Class supportType;

    public AbstractFixTypeResultMerger() {
        TypeToken<R> typeToken = new TypeToken<R>(getClass()) {};
        this.supportType = (Class) typeToken.getRawType();
    }

    @Override
    public final boolean support(Class<R> resultType) {
        if (resultType == null){
            return false;
        }
        return this.supportType.isAssignableFrom(resultType);
    }
}

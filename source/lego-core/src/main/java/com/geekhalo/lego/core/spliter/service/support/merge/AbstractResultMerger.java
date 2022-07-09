package com.geekhalo.lego.core.spliter.service.support.merge;

import com.geekhalo.lego.core.spliter.service.ResultMerger;
import com.geekhalo.lego.core.spliter.service.SmartResultMerger;
import com.google.common.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 *
 */
abstract class AbstractResultMerger<R> implements SmartResultMerger<R> {
    private final Class supportType;

    public AbstractResultMerger() {
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

    @Override
    public final R merge(List<R> rs) {
        if (CollectionUtils.isEmpty(rs)){
            return defaultValue();
        }

        return doMerge(rs);
    }

    abstract R doMerge(List<R> rs);

    protected R defaultValue(){
        return null;
    }
}

package com.geekhalo.lego.core.spliter.service.support.merger;

import com.geekhalo.lego.core.spliter.service.ResultMerger;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class AbstractResultMerger<R>
    implements ResultMerger<R> {

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

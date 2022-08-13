package com.geekhalo.lego.core.spliter.support.spliter;


import com.geekhalo.lego.common.splitter.SplittableParam;
import com.geekhalo.lego.core.spliter.ParamSplitter;

import java.util.List;

/**
 * Created by taoli on 2022/7/18.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * SplittableParam 拆分器
 *
 */
public class SplittableParamSplitter<P extends SplittableParam<P>>
        extends AbstractFixTypeParamSplitter<P>
        implements ParamSplitter<P> {

    @Override
    protected List<P> doSplit(P param, int maxSize) {
        return param.split(maxSize);
    }
}

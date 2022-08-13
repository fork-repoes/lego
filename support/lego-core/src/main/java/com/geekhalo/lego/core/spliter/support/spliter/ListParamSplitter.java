package com.geekhalo.lego.core.spliter.support.spliter;

import com.geekhalo.lego.core.spliter.SmartParamSplitter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * List 拆分器
 *
 */
public class ListParamSplitter
        extends AbstractFixTypeParamSplitter<List>
        implements SmartParamSplitter<List> {

    @Override
    protected List<List> doSplit(List param, int maxSize) {
        return Lists.partition(param, maxSize);
    }
}

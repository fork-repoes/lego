package com.geekhalo.lego.core.spliter.service.support.merger;

import com.geekhalo.lego.core.spliter.service.SmartResultMerger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class SetResultMerger
    extends AbstractFixTypeResultMerger<Set>
    implements SmartResultMerger<Set> {
    @Override
    Set doMerge(List<Set> sets) {
        return (Set) sets.stream()
                .flatMap(s -> s.stream())
                .collect(Collectors.toSet());
    }
}

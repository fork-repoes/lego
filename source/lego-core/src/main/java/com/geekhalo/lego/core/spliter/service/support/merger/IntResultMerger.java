package com.geekhalo.lego.core.spliter.service.support.merger;

import com.geekhalo.lego.core.spliter.service.SmartResultMerger;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class IntResultMerger
        extends AbstractFixTypeResultMerger<Integer>
        implements SmartResultMerger<Integer> {
    @Override
    Integer doMerge(List<Integer> integers) {
        if (CollectionUtils.isEmpty(integers)){
            return 0;
        }
        return integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}

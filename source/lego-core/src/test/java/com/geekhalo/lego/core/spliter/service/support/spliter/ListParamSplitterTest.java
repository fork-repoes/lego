package com.geekhalo.lego.core.spliter.service.support.spliter;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class ListParamSplitterTest {
    private ListParamSplitter paramSplitter = new ListParamSplitter();

    @Test
    public void support() {
        {
            boolean support = this.paramSplitter.support(null);
            Assertions.assertFalse(support);
        }

        {
            boolean support = this.paramSplitter.support(List.class);
            Assertions.assertTrue(support);
        }
    }

    @Test
    public void split() {
        {
            List<List> split = this.paramSplitter.split(Lists.emptyList(), 1);
            Assertions.assertTrue(CollectionUtils.isEmpty(split));
        }

        {
            List<List> split = this.paramSplitter.split(Arrays.asList(1L), 1);
            Assertions.assertEquals(1, split.size());
        }

        {
            List<List> split = this.paramSplitter.split(Arrays.asList(1L, 2L, 3L, 4L, 5L), 1);
            Assertions.assertEquals(5, split.size());
        }

        {
            List<List> split = this.paramSplitter.split(Arrays.asList(1L, 2L, 3L, 4L, 5L), 3);
            Assertions.assertEquals(2, split.size());
        }
    }
}
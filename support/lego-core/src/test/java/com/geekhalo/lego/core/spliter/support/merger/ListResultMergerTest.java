package com.geekhalo.lego.core.spliter.support.merger;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class ListResultMergerTest {
    private ListResultMerger resultMerger = new ListResultMerger();

    @Test
    public void support() {
        {
            boolean support = this.resultMerger.support(null);
            Assertions.assertFalse(support);
        }

        {
            boolean support = this.resultMerger.support(List.class);
            Assertions.assertTrue(support);
        }
    }

    @Test
    public void merge() {
        {
            List<List> params = Lists.newArrayList();
            List merge = this.resultMerger.merge(params);
            Assertions.assertTrue(CollectionUtils.isEmpty(merge));
        }

        {
            List<List> params = Lists.newArrayList();
            params.add(Arrays.asList(1L));
            List merge = this.resultMerger.merge(params);
            Assertions.assertEquals(1, merge.size());
        }

        {
            List<List> params = Lists.newArrayList();
            params.add(Arrays.asList(1L, 2L, 3L));
            params.add(Arrays.asList(1L, 2L, 3L));
            List merge = this.resultMerger.merge(params);
            Assertions.assertEquals(6, merge.size());
        }

        {
            List<List> params = Lists.newArrayList();
            params.add(Arrays.asList());
            params.add(Arrays.asList(1L, 2L, 3L));
            List merge = this.resultMerger.merge(params);
            Assertions.assertEquals(3, merge.size());
        }

        {
            List<List> params = Lists.newArrayList();
            params.add(null);
            params.add(Arrays.asList(1L, 2L, 3L));
            List merge = this.resultMerger.merge(params);
            Assertions.assertEquals(3, merge.size());
        }
    }
}
package com.geekhalo.lego.core.spliter.service.support.spliter;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SetParamSplitterTest {
    private SetParamSplitter paramSplitter = new SetParamSplitter();

    @Test
    public void support() {
        {
            boolean support = this.paramSplitter.support(null);
            Assertions.assertFalse(support);
        }

        {
            boolean support = this.paramSplitter.support(Set.class);
            Assertions.assertTrue(support);
        }
    }

    @Test
    public void split() {
        {
            List<Set> split = this.paramSplitter.split(new HashSet(), 1);
            Assertions.assertTrue(CollectionUtils.isEmpty(split));
        }

        {
            List<Set> split = this.paramSplitter.split(Sets.newHashSet(1L), 1);
            Assertions.assertEquals(1, split.size());
        }

        {
            List<Set> split = this.paramSplitter.split(Sets.newHashSet(1L, 2L, 3L, 4L, 5L), 1);
            Assertions.assertEquals(5, split.size());
        }

        {
            List<Set> split = this.paramSplitter.split(Sets.newHashSet(1L, 2L, 3L, 4L, 5L), 3);
            Assertions.assertEquals(2, split.size());
        }
    }
}
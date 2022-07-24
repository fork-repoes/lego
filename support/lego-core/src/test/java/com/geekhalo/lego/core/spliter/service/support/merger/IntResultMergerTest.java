package com.geekhalo.lego.core.spliter.service.support.merger;

import com.geekhalo.lego.core.spliter.service.SmartResultMerger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class IntResultMergerTest {
    private SmartResultMerger<Integer> resultMerger = new IntResultMerger();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void support() {
        {
            Assertions.assertFalse(this.resultMerger.support(null));
        }

        {
            Assertions.assertTrue(this.resultMerger.support(Integer.class));
        }
    }

    @Test
    void merge() {
        List<Integer> data = Arrays.asList(1, 3, 4, 5);

        Integer merge = this.resultMerger.merge(data);

        Assertions.assertEquals(13, merge.intValue());
    }
}
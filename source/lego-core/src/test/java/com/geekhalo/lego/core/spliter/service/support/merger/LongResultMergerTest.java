package com.geekhalo.lego.core.spliter.service.support.merger;

import com.geekhalo.lego.core.spliter.service.ResultMerger;
import com.geekhalo.lego.core.spliter.service.SmartResultMerger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class LongResultMergerTest {
    private SmartResultMerger<Long> resultMerger = new LongResultMerger();

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
            Assertions.assertTrue(this.resultMerger.support(Long.class));
        }
    }

    @Test
    void merge() {
        List<Long> data = Arrays.asList(1L, 3L, 4L, 5L);

        Long merge = this.resultMerger.merge(data);

        Assertions.assertEquals(13L, merge.longValue());
    }
}
package com.geekhalo.lego.core.spliter.support.spliter;

import com.geekhalo.lego.common.splitter.SplittableParam;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SplittableParamSplitterTest {
    private SplittableParamSplitter paramSplitter = new SplittableParamSplitter();


    @Test
    public void split() {
        {
            List<TestParam> testParams = this.paramSplitter.split(new TestParam(Arrays.asList(1L)), 3);
            Assertions.assertEquals(1, testParams.size());
            Assertions.assertEquals(Arrays.asList(1L), testParams.get(0).numbers);
        }
        {
            List<TestParam> testParams = this.paramSplitter.split(new TestParam(Arrays.asList(1L, 2L, 3L)), 3);
            Assertions.assertEquals(1, testParams.size());
            Assertions.assertEquals(Arrays.asList(1L, 2L, 3L), testParams.get(0).numbers);
        }
        {
            List<TestParam> testParams = this.paramSplitter.split(new TestParam(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)), 3);
            Assertions.assertEquals(4, testParams.size());
            Assertions.assertEquals(Arrays.asList(1L, 2L, 3L), testParams.get(0).numbers);
            Assertions.assertEquals(Arrays.asList(4L, 5L, 6L), testParams.get(1).numbers);
            Assertions.assertEquals(Arrays.asList(7L, 8L, 9L), testParams.get(2).numbers);
            Assertions.assertEquals(Arrays.asList(10L), testParams.get(3).numbers);
        }
    }

    @Test
    public void support() {
        {
            boolean support = this.paramSplitter.support(null);
            Assertions.assertFalse(support);
        }
        {
            boolean support = this.paramSplitter.support(TestParam.class);
            Assertions.assertTrue(support);
        }
    }

    static class TestParam implements SplittableParam<TestParam> {
        private final List<Long> numbers;

        TestParam(List<Long> numbers) {
            this.numbers = numbers;
        }

        @Override
        public List<TestParam> split(int maxSize) {
            return Lists.partition(this.numbers, maxSize).stream()
                    .map(TestParam::new)
                    .collect(toList());
        }
    }
}
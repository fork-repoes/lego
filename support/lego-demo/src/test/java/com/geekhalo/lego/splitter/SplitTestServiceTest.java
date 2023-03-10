package com.geekhalo.lego.splitter;

import com.geekhalo.lego.DemoApplication;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;


/**
 * Created by taoli on 2022/7/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class SplitTestServiceTest {

    @Autowired
    private SplitTestService splitTestService;

    @Test
    @Timeout(3)
    public void splitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void testSplitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void splitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void splitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void splitByListAsCount2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int count = this.splitTestService.splitByListAsCount2(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsCount2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        int count = this.splitTestService.splitByListAsCount2(params, 10L);
        Assertions.assertEquals(8, count);
    }


    @Test
    @Timeout(3)
    public void splitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params, 10L);
        Assertions.assertEquals(8, count);
    }


    @Test
    @Timeout(3)
    public void splitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void testSplitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @Test
    @Timeout(3)
    public void splitByParam() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        AnnBasedInputParam annBasedInputParam = AnnBasedInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<Long> longs = this.splitTestService.splitByParam(annBasedInputParam);
        Assertions.assertEquals(8, longs.size());
    }

    @Test
    @Timeout(3)
    public void splitByParam_Splittable() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        SplittableInputParam splittableInputParam = SplittableInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<Long> longs = this.splitTestService.splitByParam(splittableInputParam);
        Assertions.assertEquals(8, longs.size());
    }
}
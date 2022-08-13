package com.geekhalo.lego.core.spliter.support;

import com.geekhalo.lego.core.spliter.MethodExecutor;
import com.geekhalo.lego.core.spliter.ParamSplitter;
import com.geekhalo.lego.core.spliter.ResultMerger;
import com.geekhalo.lego.core.spliter.SplitService;
import com.geekhalo.lego.core.spliter.support.executor.SerialMethodExecutor;
import com.geekhalo.lego.core.spliter.support.merger.ListResultMerger;
import com.geekhalo.lego.core.spliter.support.spliter.ListParamSplitter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class DefaultSplitServiceTest {
    private SplitService<List, List> splitService;

    @BeforeEach
    void setUp() {
        ParamSplitter<List> listParamSplitter = new ListParamSplitter();
        MethodExecutor serialMethodExecutor = new SerialMethodExecutor();
        ResultMerger<List> listResultMerger = new ListResultMerger();
        SplitService<List, List> splitService = new DefaultSplitService(listParamSplitter, serialMethodExecutor, listResultMerger);
        this.splitService = splitService;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void split() {
        List<Long> params = Lists.newArrayList();
        for (int i=0; i< 100; i ++){
            params.add(i + 1L);
        }

        List split = this.splitService.split(this::service1, params, 2);
        Assertions.assertEquals(params.size(), split.size());

    }

    List<TestData> service1(List<Long> param){
        return param.stream()
                .map(l -> new TestData(l + 1))
                .collect(Collectors.toList());
    }
}
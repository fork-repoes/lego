package com.geekhalo.lego.splitter;

import com.geekhalo.lego.common.splitter.SplittableParam;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
public class SplittableInputParam implements SplittableParam<SplittableInputParam> {
    private final List<Long> numbers;
    private final Long other;

    @Override
    public List<SplittableInputParam> split(int maxSize) {
        List<List<Long>> partition = Lists.partition(this.numbers, maxSize);
        return partition.stream()
                .map(ns -> SplittableInputParam.builder()
                        .numbers(ns)
                        .other(other)
                        .build()
                ).collect(toList());
    }
}
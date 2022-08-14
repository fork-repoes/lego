package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class BeanPropertyWriterFactories {
    private final List<BeanPropertyWriterFactory> beanPropertyWriterFactories = Lists.newArrayList();

    public BeanPropertyWriterFactories(List<BeanPropertyWriterFactory> beanPropertyWriterFactories) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(beanPropertyWriterFactories));
        this.beanPropertyWriterFactories.addAll(beanPropertyWriterFactories);

        AnnotationAwareOrderComparator.sort(this.beanPropertyWriterFactories);
    }

    public BeanPropertyWriter createFor(String path, Field field) {
        return this.beanPropertyWriterFactories.stream()
                .filter(factory -> factory.support(field))
                .map(factory -> factory.createFor(path, field))
                .findFirst()
                .orElse(null);
    }
}

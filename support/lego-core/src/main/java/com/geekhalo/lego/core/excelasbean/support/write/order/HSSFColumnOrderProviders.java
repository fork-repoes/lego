package com.geekhalo.lego.core.excelasbean.support.write.order;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFColumnOrderProviders {
    private final List<HSSFColumnOrderProvider> orderProviders;

    public HSSFColumnOrderProviders(List<HSSFColumnOrderProvider> orderProviders) {
        this.orderProviders = orderProviders;
    }

    public int orderFor(AnnotatedElement annotatedElement){
        return this.orderProviders.stream()
                .filter(provider -> provider.support(annotatedElement))
                .map(provider -> provider.orderForColumn(annotatedElement))
                .findFirst()
                .orElse(0);
    }
}

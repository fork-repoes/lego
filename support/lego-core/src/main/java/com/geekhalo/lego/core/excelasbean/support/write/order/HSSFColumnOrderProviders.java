package com.geekhalo.lego.core.excelasbean.support.write.order;

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

    public int orderFor(Field field){
        return this.orderProviders.stream()
                .filter(provider -> provider.support(field))
                .map(provider -> provider.orderForColumn(field))
                .findFirst()
                .orElse(0);
    }
}

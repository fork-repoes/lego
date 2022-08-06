package com.geekhalo.lego.core.joininmemory.support;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class ProductRepository {
    public List<Product> getByIds(List<Long> ids){
        return ids.stream()
                .map(id-> Product.builder()
                        .id(id)
                        .shopId(id % 3)
                        .name("Product-" + id)
                        .build()
                ).collect(Collectors.toList());
    }
}

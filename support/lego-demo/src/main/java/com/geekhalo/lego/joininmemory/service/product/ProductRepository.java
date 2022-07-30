package com.geekhalo.lego.joininmemory.service.product;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.geekhalo.lego.util.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class ProductRepository {

    public List<Product> getByIds(List<Long> ids){
        sleepAsMS(20);
        return ids.stream()
                .map(id -> createProduct(id))
                .collect(toList());
    }

    public Product getById(Long id){
        sleepAsMS(2);
        return createProduct(id);
    }

    private Product createProduct(Long id) {
        return Product.builder()
                .id(id)
                .name("商品-" + id)
                .price(id.intValue() % 100)
                .build();
    }
}

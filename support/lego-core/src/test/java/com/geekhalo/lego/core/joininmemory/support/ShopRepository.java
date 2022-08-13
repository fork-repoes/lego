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
public class ShopRepository {
    public List<Shop> getByIds(List<Long> ids){
        return ids.stream()
                .map(id ->
                        Shop.builder()
                                .id(id)
                                .name("Shop-" + id)
                                .build()
                ).collect(Collectors.toList());
    }
}

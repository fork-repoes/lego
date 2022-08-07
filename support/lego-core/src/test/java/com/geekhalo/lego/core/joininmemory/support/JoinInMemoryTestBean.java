package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import lombok.Data;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class JoinInMemoryTestBean {
    private final Order order;

    @JoinInMemory(keyFromSourceData = "#{order.userId}",
            keyFromJoinData = "#{id}",
            loader = "#{@userRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.core.joininmemory.support.UserVO).apply(#root)}")
    private UserVO user;

    @JoinInMemory(keyFromSourceData = "#{order.productId}",
            keyFromJoinData = "#{id}",
            loader = "#{@productRepository.getByIds(#root)}"
    )
    private Product product;

    @JoinInMemory(keyFromSourceData = "#{product.id}",
            keyFromJoinData = "#{id}",
            loader = "#{@shopRepository.getByIds(#root)}"
    )
    private Shop shop;
}

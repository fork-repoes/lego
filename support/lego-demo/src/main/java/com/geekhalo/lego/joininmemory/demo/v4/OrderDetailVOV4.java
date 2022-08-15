package com.geekhalo.lego.joininmemory.demo.v4;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.joininmemory.demo.OrderDetailVO;
import com.geekhalo.lego.joininmemory.demo.AddressVO;
import com.geekhalo.lego.joininmemory.demo.OrderVO;
import com.geekhalo.lego.joininmemory.demo.ProductVO;
import com.geekhalo.lego.joininmemory.demo.UserVO;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class OrderDetailVOV4 extends OrderDetailVO {
    private final OrderVO order;
    @JoinInMemory(keyFromSourceData = "#{order.userId}",
            keyFromJoinData = "#{id}",
            loader = "#{@userRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.UserVO).apply(#root)}"
        )
    private UserVO user;

    @JoinInMemory(keyFromSourceData = "#{order.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.AddressVO).apply(#root)}"
    )
    private AddressVO address;

    @JoinInMemory(keyFromSourceData = "#{order.productId}",
            keyFromJoinData = "#{id}",
            loader = "#{@productRepository.getByIds(#root)}",
            joinDataConverter = "#{T(com.geekhalo.lego.joininmemory.demo.ProductVO).apply(#root)}"
    )
    private ProductVO product;
}

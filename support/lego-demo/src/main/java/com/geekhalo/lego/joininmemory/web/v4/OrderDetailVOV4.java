package com.geekhalo.lego.joininmemory.web.v4;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.joininmemory.web.OrderDetailVO;
import com.geekhalo.lego.joininmemory.web.AddressVO;
import com.geekhalo.lego.joininmemory.web.OrderVO;
import com.geekhalo.lego.joininmemory.web.ProductVO;
import com.geekhalo.lego.joininmemory.web.UserVO;
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
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.UserVO).apply(#root)}"
        )
    private UserVO user;

    @JoinInMemory(keyFromSourceData = "#{order.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.AddressVO).apply(#root)}"
    )
    private AddressVO address;

    @JoinInMemory(keyFromSourceData = "#{order.productId}",
            keyFromJoinData = "#{id}",
            loader = "#{@productRepository.getByIds(#root)}",
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.ProductVO).apply(#root)}"
    )
    private ProductVO product;

}

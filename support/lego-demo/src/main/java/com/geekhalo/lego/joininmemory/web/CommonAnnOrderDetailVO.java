package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import com.geekhalo.lego.joininmemory.web.vo.AddressVO;
import com.geekhalo.lego.joininmemory.web.vo.OrderVO;
import com.geekhalo.lego.joininmemory.web.vo.ProductVO;
import com.geekhalo.lego.joininmemory.web.vo.UserVO;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CommonAnnOrderDetailVO extends BaseOrderDetailVO {
    private final OrderVO order;
    @JoinInMemory(keyFromSourceData = "#{order.userId}",
            keyFromJoinData = "#{id}",
            loader = "#{@userRepository.getByIds(#root)}",
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.vo.UserVO).apply(#root)}"
        )
    private UserVO user;

    @JoinInMemory(keyFromSourceData = "#{order.addressId}",
            keyFromJoinData = "#{id}",
            loader = "#{@addressRepository.getByIds(#root)}",
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.vo.AddressVO).apply(#root)}"
    )
    private AddressVO address;

    @JoinInMemory(keyFromSourceData = "#{order.productId}",
            keyFromJoinData = "#{id}",
            loader = "#{@productRepository.getByIds(#root)}",
            dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.vo.ProductVO).apply(#root)}"
    )
    private ProductVO product;

}

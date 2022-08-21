package com.geekhalo.lego.joininmemory.demo.v6;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemeoryExecutorType;
import com.geekhalo.lego.annotation.joininmemory.JoinInMemoryConfig;
import com.geekhalo.lego.joininmemory.JoinAddressVOOnId;
import com.geekhalo.lego.joininmemory.JoinProductVOOnId;
import com.geekhalo.lego.joininmemory.JoinUserVOOnId;
import com.geekhalo.lego.joininmemory.demo.*;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@JoinInMemoryConfig(executorType = JoinInMemeoryExecutorType.PARALLEL)
public class OrderDetailVOV6 extends OrderDetailVO {
    private final OrderVO order;

    @JoinUserVOOnId(keyFromSourceData = "#{order.userId}")
    private UserVO user;

    @JoinAddressVOOnId(keyFromSourceData = "#{order.addressId}")
    private AddressVO address;

    @JoinProductVOOnId(keyFromSourceData = "#{order.productId}")
    private ProductVO product;

}

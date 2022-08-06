package com.geekhalo.lego.joininmemory.web.v4;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemeoryExecutorType;
import com.geekhalo.lego.annotation.joininmemory.JoinInMemoryConfig;
import com.geekhalo.lego.joininmemory.service.address.JoinAddressVOOnId;
import com.geekhalo.lego.joininmemory.service.product.JoinProductVOOnId;
import com.geekhalo.lego.joininmemory.service.user.JoinUserVOOnId;
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
@JoinInMemoryConfig(executorType = JoinInMemeoryExecutorType.PARALLEL)
public class OrderDetailVOV4 extends OrderDetailVO {
    private final OrderVO order;

    @JoinUserVOOnId(keyFromSourceData = "#{order.userId}")
    private UserVO user;

    @JoinAddressVOOnId(keyFromSourceData = "#{order.addressId}")
    private AddressVO address;

    @JoinProductVOOnId(keyFromSourceData = "#{order.productId}")
    private ProductVO product;

}

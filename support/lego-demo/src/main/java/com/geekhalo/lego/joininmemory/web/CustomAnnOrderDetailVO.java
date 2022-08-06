package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemeoryExecutorType;
import com.geekhalo.lego.annotation.joininmemory.JoinInMemoryConfig;
import com.geekhalo.lego.joininmemory.service.address.JoinAddressVOOnId;
import com.geekhalo.lego.joininmemory.service.product.JoinProductVOOnId;
import com.geekhalo.lego.joininmemory.service.user.JoinUserVOOnId;
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
@JoinInMemoryConfig(executorType = JoinInMemeoryExecutorType.PARALLEL)
public class CustomAnnOrderDetailVO extends BaseOrderDetailVO {
    private final OrderVO order;
    @JoinUserVOOnId(keyFromSourceData = "#{order.userId}")
    private UserVO user;

    @JoinAddressVOOnId(keyFromSourceData = "#{order.addressId}")
    private AddressVO address;

    @JoinProductVOOnId(keyFromSourceData = "#{order.productId}")
    private ProductVO product;

}

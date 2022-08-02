package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.joininmemory.service.address.JoinAddressOnId;
import com.geekhalo.lego.joininmemory.service.product.JoinProductOnId;
import com.geekhalo.lego.joininmemory.service.user.JoinUserOnId;
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
public class AnnOrderDetailVO extends BaseOrderDetailVO {
    private final OrderVO order;
    @JoinUserOnId(userId = "order.userId")
    private UserVO user;

    @JoinAddressOnId(addressId = "order.addressId")
    private AddressVO address;

    @JoinProductOnId(productId = "order.productId")
    private ProductVO product;

}

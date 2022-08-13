package com.geekhalo.lego.joininmemory.web.v1;

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
public class OrderDetailVOV1 extends OrderDetailVO {
    private final OrderVO order;
    private UserVO user;
    private AddressVO address;
    private ProductVO product;

}

package com.geekhalo.lego.validator;

import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.stock.Stock;
import com.geekhalo.lego.service.user.User;
import lombok.Data;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CreateOrderContext {
    private User user;
    private Product product;
    private Stock stock;
    private Integer count;
}

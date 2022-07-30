package com.geekhalo.lego.joininmemory.service.product;

import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Builder
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
}

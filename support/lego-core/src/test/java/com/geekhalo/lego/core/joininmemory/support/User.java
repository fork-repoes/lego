package com.geekhalo.lego.core.joininmemory.support;

import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Builder
public class User {
    private Long id;
    private String name;
}

package com.geekhalo.lego.joininmemory.service.user;

import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Builder
@Data
public class User {
    private Long id;
    private String name;
}

package com.geekhalo.lego.singlequery;

import java.util.Date;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface User {
    Long getId();

    String getName();

    Integer getStatus();

    Date getBirthAt();

    String getMobile();
}

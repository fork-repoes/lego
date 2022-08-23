package com.geekhalo.lego.singlequery;

import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SingleQueryService {

    User oneOf(Object query);

    List<User> listOf(Object query);
}

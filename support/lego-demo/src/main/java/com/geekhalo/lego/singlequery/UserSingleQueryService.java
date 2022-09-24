package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.core.singlequery.Page;

import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface UserSingleQueryService {

    void checkFor(Class cls);

    User oneOf(Object query);

    List<? extends User> listOf(Object query);

    Long countOf(Object query);

    Page<? extends User> pageOf(Object query);
}

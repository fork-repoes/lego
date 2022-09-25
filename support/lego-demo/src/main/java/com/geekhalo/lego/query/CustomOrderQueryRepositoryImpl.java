package com.geekhalo.lego.query;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class CustomOrderQueryRepositoryImpl implements CustomOrderQueryRepository{
    @Override
    public Order getByIds(List<Long> ids) {
        return null;
    }
}

package com.geekhalo.lego.core.singlequery;

/**
 * Created by taoli on 2022/7/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 查询接口
 */
public interface SingleQueryRepository<E, ID>
        extends QueryObjectRepository<E>,
        QueryIdRepository<E, ID> {

}

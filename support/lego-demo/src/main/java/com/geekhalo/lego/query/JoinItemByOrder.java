package com.geekhalo.lego.query;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JoinInMemory(keyFromSourceData = "",
        keyFromJoinData = "#{orderId}",
        loader = "#{@orderItemQueryRepository.getByOrderIdIn(#root)}"
)
public @interface JoinItemByOrder {
    @AliasFor(
            annotation = JoinInMemory.class
    )
    String keyFromSourceData();
}

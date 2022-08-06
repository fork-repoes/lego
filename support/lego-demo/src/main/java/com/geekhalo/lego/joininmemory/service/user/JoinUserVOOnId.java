package com.geekhalo.lego.joininmemory.service.user;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JoinInMemory(keyFromSourceData = "",
        keyFromJoinData = "#{id}",
        loader = "#{@userRepository.getByIds(#root)}",
        dataConverter = "#{T(com.geekhalo.lego.joininmemory.web.vo.UserVO).apply(#root)}"
)
public @interface JoinUserVOOnId {
    @AliasFor(
            annotation = JoinInMemory.class
    )
    String keyFromSourceData();
}

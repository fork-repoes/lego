package com.geekhalo.lego.core.joininmemory.support;

import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Builder
public class UserVO {
    private Long id;
    private String name;

    public static UserVO apply(User user){
        if (user == null){
            return null;
        }
        return UserVO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}

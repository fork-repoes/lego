package com.geekhalo.lego.joininmemory.web.vo;

import com.geekhalo.lego.joininmemory.service.user.User;
import lombok.Value;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class UserVO {
    private Long id;
    private String name;

    public static UserVO apply(User user){
        if (user == null){
            return null;
        }

        return new UserVO(user.getId(), user.getName());
    }
}

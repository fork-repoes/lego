package com.geekhalo.lego.core.joininmemory.support;

import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class UserRepository {
    public List<User> getByIds(List<Long> ids){
        return ids.stream()
                .map(id-> User.builder()
                        .id(id)
                        .name("Name-" + id)
                        .build()
                ).collect(toList());
    }
}

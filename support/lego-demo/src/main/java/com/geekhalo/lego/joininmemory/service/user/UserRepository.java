package com.geekhalo.lego.joininmemory.service.user;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.geekhalo.lego.util.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class UserRepository {
    public List<User> getByIds(List<Long> ids){
        sleepAsMS(50);
        return ids.stream()
                .distinct()
                .map(id -> createUser(id))
                .collect(toList());
    }

    public User getById(Long id){
        sleepAsMS(5);
        return createUser(id);
    }

    private User createUser(Long id){
        return User.builder()
                .id(id)
                .name("用户-" + id)
                .build();
    }
}

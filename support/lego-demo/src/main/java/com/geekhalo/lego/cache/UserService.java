package com.geekhalo.lego.cache;

import com.geekhalo.lego.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2023/4/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
@Slf4j
public class UserService {

    @Cacheable(value = "localCache")
    public User getById(Long id){
        log.info("call get by id {}", id);
        return createUser(id, 5L);
    }

    private User createUser(Long id, Long sleep) {
        TimeUtils.sleepAsMS(sleep);
        User user = new User();
        user.setId(id);
        user.setName(String.valueOf(RandomUtils.nextLong()));
        return user;
    }

    @Cacheable(value = "localCache")
    public List<User> getByIds(List<Long> ids){
        log.info("call get by ids {}", ids);
        return ids.stream()
                .map(id -> createUser(id, 1L))
                .collect(Collectors.toList());
    }
}

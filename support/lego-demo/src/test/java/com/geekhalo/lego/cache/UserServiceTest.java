package com.geekhalo.lego.cache;

import com.geekhalo.lego.DemoApplication;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2023/4/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    private List<Long> ids = Lists.newArrayList();

    @BeforeEach
    void setUp() {
        ids.clear();
        for (int i = 0; i < 100; i++){
            ids.add(RandomUtils.nextLong());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void getById() {
        for (int i = 0; i< 100; i++) {
            this.ids.forEach(id -> {
                User user = this.userService.getById(id);
                Assertions.assertNotNull(user);
            });
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void getByIds() {
//        this.userService.getByIds(this.ids);
        for (int l = 0; l < 100; l++) {
            for (int i = 0; i < ids.size(); i++) {
                List<Long> idsToUse =
                        this.ids.stream()
                                .limit(i + 1)
                                .collect(Collectors.toList());
                List<User> users = this.userService.getByIds(idsToUse);
                Assertions.assertNotNull(users);
                Assertions.assertFalse(users.isEmpty());
            }
        }
    }
}
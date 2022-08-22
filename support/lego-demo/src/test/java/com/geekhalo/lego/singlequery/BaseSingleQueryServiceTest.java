package com.geekhalo.lego.singlequery;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

abstract class BaseSingleQueryServiceTest {

    abstract SingleQueryService getSingleQueryService();

    @Test
    void getByIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        QueryUserByIds queryUserByIds = new QueryUserByIds();
        queryUserByIds.setIds(ids);
        List<User> users = this.getSingleQueryService().getByIds(queryUserByIds);
        Assertions.assertNotNull(users);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
        Assertions.assertEquals(10, users.size());
    }

    @Test
    void getById() {
        for (int i = 0; i < 10; i++){
            Long id = i + 1L;
            QueryUserById byId = new QueryUserById();
            byId.setId(id);
            User user = this.getSingleQueryService().getById(byId);
            Assertions.assertNotNull(user);
        }
    }
}
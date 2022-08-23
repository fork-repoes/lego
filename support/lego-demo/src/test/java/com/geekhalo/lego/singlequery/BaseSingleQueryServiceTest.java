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
    void getById() {
        for (int i = 0; i < 10; i++){
            Long id = i + 1L;
            QueryUserById byId = new QueryUserById();
            byId.setId(id);
            User user = this.getSingleQueryService().oneOf(byId);
            Assertions.assertNotNull(user);
        }
    }

    @Test
    void getByIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        QueryUserByIds queryUserByIds = new QueryUserByIds();
        queryUserByIds.setIds(ids);
        {
            List<User> users = this.getSingleQueryService().listOf(queryUserByIds);
            Assertions.assertNotNull(users);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
            Assertions.assertEquals(10, users.size());
        }
        {
            Long count = this.getSingleQueryService().countOf(queryUserByIds);
            Assertions.assertEquals(10L, count);
        }
    }

    @Test
    void getByUserIdGreater(){
        {
            QueryUserByIdGreater queryUserByIdGreater = new QueryUserByIdGreater();
            queryUserByIdGreater.setStartUserId(1L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryUserByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(12, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryUserByIdGreater);
                Assertions.assertEquals(12L, count);
            }
        }

        {
            QueryUserByIdGreater queryUserByIdGreater = new QueryUserByIdGreater();
            queryUserByIdGreater.setStartUserId(10L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryUserByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(3, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryUserByIdGreater);
                Assertions.assertEquals(3L, count);
            }
        }

        {
            QueryUserByIdGreater queryUserByIdGreater = new QueryUserByIdGreater();
            queryUserByIdGreater.setStartUserId(13L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryUserByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(0, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(queryUserByIdGreater);
                Assertions.assertEquals(0L, count);
            }
        }
    }


    @Test
    void getByUserIdGreaterOrEquals(){
        {
            QueryUserByIdGreaterOrEquals query = new QueryUserByIdGreaterOrEquals();
            query.setStartUserId(1L);
            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(13, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(13L, count);
            }

        }

        {
            QueryUserByIdGreaterOrEquals query = new QueryUserByIdGreaterOrEquals();
            query.setStartUserId(10L);
            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(4, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(4L, count);
            }
        }

        {
            QueryUserByIdGreaterOrEquals query = new QueryUserByIdGreaterOrEquals();
            query.setStartUserId(13L);
            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(1, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(1L, count);
            }
        }
    }

    @Test
    void getByNameIsNull(){
        {
            QueryByNameIsNull queryByNameIsNull = new QueryByNameIsNull();
            queryByNameIsNull.setNull(true);

            {
                List<User> users = this.getSingleQueryService().listOf(queryByNameIsNull);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(0, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryByNameIsNull);
                Assertions.assertEquals(0L, count);
            }
        }

        {
            // TODO sql 未生效
            QueryByNameIsNull queryByNameIsNull = new QueryByNameIsNull();
            queryByNameIsNull.setNull(false);

            {
                List<User> users = this.getSingleQueryService().listOf(queryByNameIsNull);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(13, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryByNameIsNull);
                Assertions.assertEquals(13L, count);
            }
        }
    }

    @Test
    public void getByIdLess(){
        {
            QueryByLess query = new QueryByLess();
            query.setMaxId(14L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(13, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(13L, count);
            }
        }

        {
            QueryByLess query = new QueryByLess();
            query.setMaxId(10L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(9, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(9L, count);
            }
        }

        {
            QueryByLess query = new QueryByLess();
            query.setMaxId(5L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(4, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(4L, count);
            }
        }
    }

    @Test
    public void getByIdLessEqual(){
        {
            QueryByLessOrEqual query = new QueryByLessOrEqual();
            query.setMaxId(13L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(13, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(13L, count);
            }
        }

        {
            QueryByLessOrEqual query = new QueryByLessOrEqual();
            query.setMaxId(10L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(10, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(10L, count);
            }
        }

        {
            QueryByLessOrEqual query = new QueryByLessOrEqual();
            query.setMaxId(5L);

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(5, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(5L, count);
            }
        }
    }

    @Test
    void getByNameNotEqual(){
        {
            QueryByNotEqual query = new QueryByNotEqual();
            query.setName("李四");

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(13, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(13L, count);
            }
        }

        {
            QueryByNotEqual query = new QueryByNotEqual();
            query.setName("张三6");

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(12, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(12L, count);
            }
        }
        {
            QueryByNotEqual query = new QueryByNotEqual();
            query.setName("张三8");

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(12, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(12L, count);
            }
        }
    }

    @Test
    void getByNotIn(){
        {
            QueryByNotIn query = new QueryByNotIn();
            query.setIds(Arrays.asList(1L, 2L));

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(11, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(11L, count);
            }
        }


        {
            QueryByNotIn query = new QueryByNotIn();
            query.setIds(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L));

            {
                List<User> users = this.getSingleQueryService().listOf(query);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(5, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(query);
                Assertions.assertEquals(5L, count);
            }
        }

    }
}
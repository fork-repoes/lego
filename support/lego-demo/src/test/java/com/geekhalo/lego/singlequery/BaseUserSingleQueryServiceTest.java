package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.Sort;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

abstract class BaseUserSingleQueryServiceTest {

    abstract UserSingleQueryService getSingleQueryService();

    @Test
    void checkQuery(){
        getSingleQueryService().checkFor(PageByIdGreater.class);
        getSingleQueryService().checkFor(QueryByIdGreater.class);
        getSingleQueryService().checkFor(QueryByIdEq.class);
        getSingleQueryService().checkFor(QueryByIdGreaterOrEquals.class);
        getSingleQueryService().checkFor(QueryByIdIn.class);
        getSingleQueryService().checkFor(QueryByIdLess.class);
        getSingleQueryService().checkFor(QueryByIdLessOrEqual.class);
        getSingleQueryService().checkFor(QueryByIdNotIn.class);
        getSingleQueryService().checkFor(QueryByNameIsNull.class);
        getSingleQueryService().checkFor(QueryByNameNotEqual.class);
        getSingleQueryService().checkFor(QueryByStatusAndMobile.class);

    }

    @Test
    void checkQuery2(){
        Assertions.assertThrowsExactly(RuntimeException.class, ()->{
            getSingleQueryService().checkFor(ErrorQuery.class);
        });
    }

    @Data
    static class ErrorQuery{
        @FieldEqualTo("uid")
        private Long id;
    }

    @Test
    void getById() {
        for (int i = 0; i < 10; i++){
            Long id = i + 1L;
            QueryByIdEq byId = new QueryByIdEq();
            byId.setId(id);
            User user = this.getSingleQueryService().oneOf(byId);
            Assertions.assertNotNull(user);
        }
    }

    @Test
    void getByIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        QueryByIdIn queryByIdIn = new QueryByIdIn();
        queryByIdIn.setIds(ids);
        {
            List<User> users = this.getSingleQueryService().listOf(queryByIdIn);
            Assertions.assertNotNull(users);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
            Assertions.assertEquals(10, users.size());
        }
        {
            Long count = this.getSingleQueryService().countOf(queryByIdIn);
            Assertions.assertEquals(10L, count);
        }
    }

    @Test
    void getByUserIdGreater(){
        {
            QueryByIdGreater queryByIdGreater = new QueryByIdGreater();
            queryByIdGreater.setStartUserId(1L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(12, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryByIdGreater);
                Assertions.assertEquals(12L, count);
            }
        }

        {
            QueryByIdGreater queryByIdGreater = new QueryByIdGreater();
            queryByIdGreater.setStartUserId(10L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(3, users.size());
            }
            {
                Long count = this.getSingleQueryService().countOf(queryByIdGreater);
                Assertions.assertEquals(3L, count);
            }
        }

        {
            QueryByIdGreater queryByIdGreater = new QueryByIdGreater();
            queryByIdGreater.setStartUserId(13L);
            {
                List<User> users = this.getSingleQueryService().listOf(queryByIdGreater);
                Assertions.assertNotNull(users);
                Assertions.assertEquals(0, users.size());
            }

            {
                Long count = this.getSingleQueryService().countOf(queryByIdGreater);
                Assertions.assertEquals(0L, count);
            }
        }
    }


    @Test
    void getByUserIdGreaterOrEquals(){
        {
            QueryByIdGreaterOrEquals query = new QueryByIdGreaterOrEquals();
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
            QueryByIdGreaterOrEquals query = new QueryByIdGreaterOrEquals();
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
            QueryByIdGreaterOrEquals query = new QueryByIdGreaterOrEquals();
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
            QueryByIdLess query = new QueryByIdLess();
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
            QueryByIdLess query = new QueryByIdLess();
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
            QueryByIdLess query = new QueryByIdLess();
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
            QueryByIdLessOrEqual query = new QueryByIdLessOrEqual();
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
            QueryByIdLessOrEqual query = new QueryByIdLessOrEqual();
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
            QueryByIdLessOrEqual query = new QueryByIdLessOrEqual();
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
            QueryByNameNotEqual query = new QueryByNameNotEqual();
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
            QueryByNameNotEqual query = new QueryByNameNotEqual();
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
            QueryByNameNotEqual query = new QueryByNameNotEqual();
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
            QueryByIdNotIn query = new QueryByIdNotIn();
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
            QueryByIdNotIn query = new QueryByIdNotIn();
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

    @Test
    void queryByEmbeddedFilter() throws Exception{
        QueryByEmbeddedFilter query = new QueryByEmbeddedFilter();
        query.setId(0L);
        QueryByStatusAndBirth queryByStatusAndBirth = new QueryByStatusAndBirth();
        query.setStatusAndBirth(queryByStatusAndBirth);
        queryByStatusAndBirth.setStatus(1);
        queryByStatusAndBirth.setBirthAfter(DateUtils.parseDate("2018-10-01", "yyyy-MM-dd"));

        List<User> users = getSingleQueryService().listOf(query);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
    }

    @Test
    void pageOf(){
        {
            PageByIdGreater pageByIdGreater = new PageByIdGreater();
            pageByIdGreater.setStartId(0L);
            Pageable pageable = new Pageable();
            pageByIdGreater.setPageable(pageable);
            pageable.setPageNo(0);
            pageable.setPageSize(5);

            Sort sort = new Sort();
            pageByIdGreater.setSort(sort);
            Sort.Order order = Sort.Order.<Orders>builder()
                    .orderField(Orders.ID)
                    .direction(Sort.Direction.ASC)
                    .build();

            sort.getOrders().add(order);

            Page<User> userPage = this.getSingleQueryService().pageOf(pageByIdGreater);

            Assertions.assertTrue(userPage.hasContent());
            Assertions.assertEquals(5, userPage.getContent().size());

            Assertions.assertEquals(0, userPage.getCurrentPage());
            Assertions.assertEquals(5, userPage.getPageSize());
            Assertions.assertEquals(3, userPage.getTotalPages());
            Assertions.assertEquals(13, userPage.getTotalElements());

            Assertions.assertTrue(userPage.isFirst());
            Assertions.assertFalse( userPage.hasPrevious());

            Assertions.assertTrue( userPage.hasNext());
            Assertions.assertFalse(userPage.isLast());


        }

        {
            PageByIdGreater pageByIdGreater = new PageByIdGreater();
            pageByIdGreater.setStartId(0L);
            Pageable pageable = new Pageable();
            pageByIdGreater.setPageable(pageable);
            pageable.setPageNo(1);
            pageable.setPageSize(5);

            Sort sort = new Sort();
            pageByIdGreater.setSort(sort);
            Sort.Order order = Sort.Order.<Orders>builder()
                    .orderField(Orders.ID)
                    .direction(Sort.Direction.ASC)
                    .build();

            sort.getOrders().add(order);

            Page<User> userPage = this.getSingleQueryService().pageOf(pageByIdGreater);
            Assertions.assertTrue(userPage.hasContent());
            Assertions.assertEquals(5, userPage.getContent().size());

            Assertions.assertEquals(1, userPage.getCurrentPage());
            Assertions.assertEquals(5, userPage.getPageSize());
            Assertions.assertEquals(3, userPage.getTotalPages());
            Assertions.assertEquals(13, userPage.getTotalElements());

            Assertions.assertFalse(userPage.isFirst());
            Assertions.assertTrue( userPage.hasPrevious());

            Assertions.assertTrue( userPage.hasNext());
            Assertions.assertFalse(userPage.isLast());
        }

        {
            PageByIdGreater pageByIdGreater = new PageByIdGreater();
            pageByIdGreater.setStartId(0L);
            Pageable pageable = new Pageable();
            pageByIdGreater.setPageable(pageable);
            pageable.setPageNo(2);
            pageable.setPageSize(5);

            Sort sort = new Sort();
            pageByIdGreater.setSort(sort);
            Sort.Order order = Sort.Order.<Orders>builder()
                    .orderField(Orders.ID)
                    .direction(Sort.Direction.ASC)
                    .build();

            sort.getOrders().add(order);

            Page<User> userPage = this.getSingleQueryService().pageOf(pageByIdGreater);
            Assertions.assertTrue(userPage.hasContent());
            Assertions.assertEquals(3, userPage.getContent().size());

            Assertions.assertEquals(2, userPage.getCurrentPage());
            Assertions.assertEquals(5, userPage.getPageSize());
            Assertions.assertEquals(3, userPage.getTotalPages());
            Assertions.assertEquals(13, userPage.getTotalElements());

            Assertions.assertFalse(userPage.isFirst());
            Assertions.assertTrue( userPage.hasPrevious());

            Assertions.assertFalse( userPage.hasNext());
            Assertions.assertTrue(userPage.isLast());
        }
    }

    @Test
    void queryByStatusAndMobile() throws Exception{
        QueryByStatusAndMobile query = new QueryByStatusAndMobile();
        query.setStatus(1);
        query.setMobile("18729901871");
        query.setBirthAfter(DateUtils.parseDate("2018-10-01", "yyyy-MM-dd"));

        List<User> users = getSingleQueryService().listOf(query);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
    }

    @Test
    void queryByIdGreaterWithMaxResult(){
        QueryByIdGreaterWithMaxResult query = new QueryByIdGreaterWithMaxResult();
        query.setStartUserId(0L);

        List<User> users = getSingleQueryService().listOf(query);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(users));
    }
}
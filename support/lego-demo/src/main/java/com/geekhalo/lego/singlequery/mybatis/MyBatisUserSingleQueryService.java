package com.geekhalo.lego.singlequery.mybatis;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.mybatis.support.AbstractReflectBasedExampleSingleQueryRepository;
import com.geekhalo.lego.singlequery.User;
import com.geekhalo.lego.singlequery.UserSingleQueryService;
import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUser;
import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUserExample;
import com.geekhalo.lego.singlequery.mybatis.auto.MyBatisUserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class MyBatisUserSingleQueryService
        extends AbstractReflectBasedExampleSingleQueryRepository
        implements UserSingleQueryService {
    public MyBatisUserSingleQueryService(MyBatisUserMapper mapper){
        super(mapper, MyBatisUserExample.class);
    }
    @Override
    public List<User> listOf(Object query) {
        return super.listOf(query);
    }

    @Override
    public Page<User> pageOf(Object query) {
        return super.pageOf(query);
    }

    @Override
    public void checkFor(Class cls) {
        getExampleConverter().validate(cls);
    }

    @Override
    public User oneOf(Object query){
        return (MyBatisUser) get(query);
    }
}

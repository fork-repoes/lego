package com.geekhalo.lego.singlequery.mybatis;

import com.geekhalo.lego.core.singlequery.mybatis.support.AbstractReflectBasedExampleQueryRepository;
import com.geekhalo.lego.singlequery.*;
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
public class MyBatisSingleQueryService
        extends AbstractReflectBasedExampleQueryRepository
        implements SingleQueryService {
    public MyBatisSingleQueryService(MyBatisUserMapper mapper){
        super(mapper, MyBatisUserExample.class);
    }
    @Override
    public List<User> listOf(Object query) {
        return super.listOf(query);
    }

    @Override
    public User oneOf(Object query){
        return (MyBatisUser) get(query);
    }
}

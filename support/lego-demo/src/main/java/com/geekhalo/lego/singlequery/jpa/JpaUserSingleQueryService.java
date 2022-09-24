package com.geekhalo.lego.singlequery.jpa;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.support.BaseSpecificationQueryObjectRepository;
import com.geekhalo.lego.singlequery.User;
import com.geekhalo.lego.singlequery.UserSingleQueryService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class JpaUserSingleQueryService
    extends BaseSpecificationQueryObjectRepository
    implements UserSingleQueryService {

    public JpaUserSingleQueryService(JpaUserRepository specificationExecutor,
                                     SpecificationConverterFactory specificationConverterFactory) {
        super(specificationExecutor, JpaUser.class, specificationConverterFactory);
    }

    @Override
    public void checkFor(Class cls) {
        getSpecificationConverter().validate(cls);
    }

    @Override
    public User oneOf(Object query) {
        return (User) super.get(query);
    }

    @Override
    public List<User> listOf(Object query) {
        return super.listOf(query);
    }

    @Override
    public Long countOf(Object query) {
        return super.countOf(query);
    }

    @Override
    public Page<User> pageOf(Object query) {
        return super.pageOf(query);
    }
}

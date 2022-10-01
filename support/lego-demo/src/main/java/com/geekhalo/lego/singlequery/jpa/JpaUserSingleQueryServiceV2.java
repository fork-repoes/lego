package com.geekhalo.lego.singlequery.jpa;

import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.singlequery.User;
import com.geekhalo.lego.singlequery.UserSingleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class JpaUserSingleQueryServiceV2 implements UserSingleQueryService {
    @Autowired
    private JpaUserRepositoryV2 jpaUserRepository;

    @Override
    public void checkFor(Class cls) {
        jpaUserRepository.checkForQueryObject(cls);
    }

    @Override
    public User oneOf(Object query) {
        return jpaUserRepository.get(query);
    }

    @Override
    public List<? extends User> listOf(Object query) {
        return jpaUserRepository.listOf(query);
    }

    @Override
    public Long countOf(Object query) {
        return jpaUserRepository.countOf(query);
    }

    @Override
    public Page<? extends User> pageOf(Object query) {
        return this.jpaUserRepository.pageOf(query);
    }
}

package com.geekhalo.lego.singlequery.jpa;

import com.geekhalo.lego.core.singlequery.QueryObjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface JpaUserRepositoryV2
        extends JpaRepository<JpaUser, Long> ,
        QueryObjectRepository<JpaUser> {
}

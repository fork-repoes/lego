package com.geekhalo.lego.singlequery.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface JpaUserRepository extends Repository<JpaUser, Long>, JpaSpecificationExecutor<JpaUser> {
}

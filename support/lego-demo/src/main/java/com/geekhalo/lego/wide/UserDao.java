package com.geekhalo.lego.wide;

import com.geekhalo.lego.service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface UserDao extends JpaRepository<User, Long> {
}

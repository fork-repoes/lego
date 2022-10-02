package com.geekhalo.lego.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository("orderRepositoryForCommand")
public interface OrderRepository extends JpaRepository<Order, Long> {
}

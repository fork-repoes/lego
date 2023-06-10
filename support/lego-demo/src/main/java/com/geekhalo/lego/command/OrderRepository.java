package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.CommandWithKeyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository("orderRepositoryForCommand")
public interface OrderRepository extends JpaRepository<Order, Long>,
        CommandWithKeyRepository<Order, Long, Long> {

    @Override
    default Order sync(Order entity){
        return save(entity);
    }

    @Override
    default Optional<Order> findByKey(Long key){
        return findById(key);
    }
}

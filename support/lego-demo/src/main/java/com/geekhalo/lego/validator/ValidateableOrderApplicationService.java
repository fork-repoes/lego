package com.geekhalo.lego.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by taoli on 2022/9/19.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class ValidateableOrderApplicationService {
    @Autowired
    private ValidateableOrderRepository repository;

    public void createOrder(ValidateableOrder order){
        this.repository.save(order);
    }
}

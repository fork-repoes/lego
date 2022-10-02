package com.geekhalo.lego.command;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.query.OrderStatus;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Transactional
class OrderCommandServiceTest extends BaseOrderCommandServiceTest{
    @Autowired
    private OrderCommandServiceImpl orderCommandService;

    @Override
    OrderCommandService orderCommandService() {
        return orderCommandService;
    }
}
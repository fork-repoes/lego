package com.geekhalo.lego.idempotent;

import com.geekhalo.lego.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class RedisBasesIdempotentServiceTest extends BaseIdempotentServiceTest{
    @Autowired
    private RedisBasedIdempotentService idempotentService;
    @Override
    BaseIdempotentService getIdempotentService() {
        return idempotentService;
    }
}

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
public class DBBasesIdempotentServiceTest extends BaseIdempotentServiceTest{
    @Autowired
    private DBBasedIdempotentService idempotentService;
    @Override
    BaseIdempotentService getIdempotentService() {
        return idempotentService;
    }
}

package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.singlequery.mybatis.MyBatisUserSingleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class MyBatisSingeQueryServiceTest extends BaseUserSingleQueryServiceTest {
    @Autowired
    private MyBatisUserSingleQueryService singleQueryService;
    @Override
    UserSingleQueryService getSingleQueryService() {
        return singleQueryService;
    }
}

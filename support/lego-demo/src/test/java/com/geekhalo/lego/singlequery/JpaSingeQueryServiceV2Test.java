package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.singlequery.jpa.JpaUserSingleQueryServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class JpaSingeQueryServiceV2Test extends BaseUserSingleQueryServiceTest {
    @Autowired
    private JpaUserSingleQueryServiceV2 singleQueryService;
    @Override
    UserSingleQueryService getSingleQueryService() {
        return singleQueryService;
    }
}

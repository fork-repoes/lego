package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.singlequery.mybatis.MyBatisSingleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class MyBatisSingeQueryServiceTest extends BaseSingleQueryServiceTest{
    @Autowired
    private MyBatisSingleQueryService singleQueryService;
    @Override
    SingleQueryService getSingleQueryService() {
        return singleQueryService;
    }
}

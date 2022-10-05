package com.geekhalo.lego.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@MapperScan(basePackages = "com.geekhalo.lego.singlequery.mybatis")
public class MyBatisConfiguration {
}

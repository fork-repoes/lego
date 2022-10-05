package com.geekhalo.lego.config;

import com.geekhalo.lego.core.query.EnableQueryService;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@EnableQueryService(basePackages = "com.geekhalo.lego.query")
public class QueryServiceConfiguration {
}

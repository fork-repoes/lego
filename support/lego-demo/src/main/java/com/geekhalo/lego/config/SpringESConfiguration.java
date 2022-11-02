package com.geekhalo.lego.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by taoli on 2022/11/1.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.geekhalo.lego.wide.es")
public class SpringESConfiguration {
}

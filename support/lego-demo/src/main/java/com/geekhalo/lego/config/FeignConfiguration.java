package com.geekhalo.lego.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/11/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@EnableFeignClients(basePackages = "com.geekhalo.lego.feign")
@Configuration
public class FeignConfiguration {
}

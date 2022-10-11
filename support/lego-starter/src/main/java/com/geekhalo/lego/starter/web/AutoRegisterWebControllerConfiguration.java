package com.geekhalo.lego.starter.web;

//import com.geekhalo.lego.core.web.WebMethodHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/10/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnWebApplication
public class AutoRegisterWebControllerConfiguration {
//    @Bean
//    public WebMethodHandlerMapping webMethodHandlerMapping(){
//        WebMethodHandlerMapping handlerMapping = new WebMethodHandlerMapping();
//        handlerMapping.setOrder(0);
//        return handlerMapping;
//    }
}

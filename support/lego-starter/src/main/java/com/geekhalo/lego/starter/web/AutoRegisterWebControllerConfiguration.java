package com.geekhalo.lego.starter.web;

//import com.geekhalo.lego.core.web.WebMethodHandlerMapping;

import com.geekhalo.lego.core.web.support.DefaultRestResultFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by taoli on 2022/10/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnWebApplication
@Import({CommandWebConfiguration.class, QueryWebConfiguration.class})
public class AutoRegisterWebControllerConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DefaultRestResultFactory defaultRestResultFactory(){
        return new DefaultRestResultFactory();
    }

}

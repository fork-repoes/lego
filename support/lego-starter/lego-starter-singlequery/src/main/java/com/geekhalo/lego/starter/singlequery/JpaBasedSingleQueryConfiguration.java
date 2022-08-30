package com.geekhalo.lego.starter.singlequery;

import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.support.DefaultSpecificationConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class JpaBasedSingleQueryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpecificationConverter specificationConverter(){
        return new DefaultSpecificationConverter();
    }
}

package com.geekhalo.lego.starter.singlequery;

import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.support.DefaultSpecificationConverter;
import com.geekhalo.lego.core.singlequery.jpa.support.DefaultSpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.support.handler.JpaAnnotationHandler;
import com.geekhalo.lego.core.singlequery.jpa.support.handler.JpaFieldEqualToHandler;
import com.geekhalo.lego.core.singlequery.jpa.support.handler.JpaGreaterThanHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class JpaBasedSingleQueryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpecificationConverterFactory specificationConverterFactory(List<JpaAnnotationHandler> annotationHandlers){
        return new DefaultSpecificationConverterFactory(annotationHandlers);
    }

    @Bean
    public JpaFieldEqualToHandler jpaFieldEqualToHandler(){
        return new JpaFieldEqualToHandler();
    }

    @Bean
    public JpaGreaterThanHandler jpaGreaterThanHandler(){
        return new JpaGreaterThanHandler();
    }
}

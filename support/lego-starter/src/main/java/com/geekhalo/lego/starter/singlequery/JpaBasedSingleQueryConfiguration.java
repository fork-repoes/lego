package com.geekhalo.lego.starter.singlequery;

import com.geekhalo.lego.core.singlequery.jpa.SpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.support.DefaultSpecificationConverterFactory;
import com.geekhalo.lego.core.singlequery.jpa.support.handler.*;
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
    public JpaFieldGreaterThanHandler jpaGreaterThanHandler(){
        return new JpaFieldGreaterThanHandler();
    }

    @Bean
    public JpaFieldGreaterThanOrEqualToHandler jpaFieldGreaterThanOrEqualToHandler(){
        return new JpaFieldGreaterThanOrEqualToHandler();
    }

    @Bean
    public JpaFieldInHandler jpaFieldInHandler(){
        return new JpaFieldInHandler();
    }

    @Bean
    public JpaFieldIsNullHandler jpaFieldIsNullHandler(){
        return new JpaFieldIsNullHandler();
    }

    @Bean
    public JpaFieldLessThanHandler jpaFieldLessThanHandler(){
        return new JpaFieldLessThanHandler();
    }

    @Bean
    public JpaFieldLessThanOrEqualToHandler jpaFieldLessThanOrEqualToHandler(){
        return new JpaFieldLessThanOrEqualToHandler();
    }

    @Bean
    public JpaFieldNotEqualToHandler jpaFieldNotEqualToHandler(){
        return new JpaFieldNotEqualToHandler();
    }

    @Bean
    public JpaFieldNotInHandler jpaFieldNotInHandler(){
        return new JpaFieldNotInHandler();
    }
}

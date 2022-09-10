package com.geekhalo.lego.starter.validator;

import com.geekhalo.lego.annotation.spliter.Split;
import com.geekhalo.lego.common.validator.ValidateErrorReporter;
import com.geekhalo.lego.common.validator.Validateable;
import com.geekhalo.lego.core.spliter.support.spring.SplitInterceptor;
import com.geekhalo.lego.core.validator.ValidateableBasedValidator;
import com.geekhalo.lego.core.validator.ValidateableMethodValidationInterceptor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnClass(Validateable.class)
public class ValidatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ValidateableBasedValidator validateableBasedValidator(){
        return new ValidateableBasedValidator();
    }

    @Bean
    public ValidateableMethodValidationInterceptor validateableMethodValidationInterceptor(){
        return new ValidateableMethodValidationInterceptor(validateableBasedValidator(),
                validateErrorReporter());
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidateErrorReporter validateErrorReporter(){
        return errors -> {
            throw new RuntimeException(errors.getErrors().toString());
        };
    }

    @Bean
    public PointcutAdvisor validateableMethodValidationAdvisor(@Autowired ValidateableMethodValidationInterceptor interceptor){
        return new DefaultPointcutAdvisor(
                new AnnotationMatchingPointcut(Validated.class, null , true),
                interceptor);
    }
}

package com.geekhalo.lego.starter.validator;

import com.geekhalo.lego.common.validator.ValidateErrorsHandler;
import com.geekhalo.lego.common.validator.Validateable;
import com.geekhalo.lego.core.validator.ValidateService;
import com.geekhalo.lego.core.validator.ValidateableBasedValidator;
import com.geekhalo.lego.core.validator.ValidateableMethodValidationInterceptor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnClass(Validateable.class)
@AutoConfigureAfter(ValidationAutoConfiguration.class)
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
    public ValidateErrorsHandler validateErrorReporter(){
        return errors -> {
            throw new RuntimeException(errors.getErrors().toString());
        };
    }

    @Bean
    public PointcutAdvisor validateableMethodValidationAdvisor(@Autowired ValidateableMethodValidationInterceptor interceptor){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(
                new AnnotationMatchingPointcut(Validated.class, null , true),
                interceptor);
        pointcutAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        return pointcutAdvisor;
    }

    @Autowired
    public void configMethodValidationOrder(List<MethodValidationPostProcessor> methodValidationPostProcessors){
        methodValidationPostProcessors.forEach(methodValidationPostProcessor ->
                methodValidationPostProcessor.setBeforeExistingAdvisors(true));
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidateService validateService(){
        return new ValidateService();
    }

}

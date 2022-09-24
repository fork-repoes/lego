package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.Set;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class ValidateService {
    private List<BeanValidator> beanValidators = Lists.newArrayList();

    @Value("${validateService.blacklist:[]}")
    private Set<String> blacklist;

    public void validate(Object bean, ValidateErrorHandler handler){
        this.beanValidators.stream()
                .filter(beanValidator -> notInBlackList(beanValidator.id()))
                .filter(beanValidator -> beanValidator.support(bean))
                .forEach(beanValidator -> beanValidator.validate(bean, handler));
    }

    public void validate(Object bean){
        this.beanValidators.stream()
                .filter(beanValidator -> notInBlackList(beanValidator.id()))
                .filter(beanValidator -> beanValidator.support(bean))
                .forEach(beanValidator -> beanValidator.validate(bean));
    }

    private boolean notInBlackList(String id) {
        return !this.blacklist.contains(id);
    }

    @Autowired(required = false)
    public void setBeanValidators(List<BeanValidator> beanValidators){
        this.beanValidators.addAll(beanValidators);
        AnnotationAwareOrderComparator.sort(this.beanValidators);
    }
}

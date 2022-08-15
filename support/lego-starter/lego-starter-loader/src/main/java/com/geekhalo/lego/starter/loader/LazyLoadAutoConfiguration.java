package com.geekhalo.lego.starter.loader;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.loader.support.DefaultLazyLoadProxyFactory;
import com.geekhalo.lego.core.loader.support.PropertyLazyLoaderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by taoli on 2022/8/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class LazyLoadAutoConfiguration {

    @Bean
    public LazyLoadProxyFactory lazyLoadProxyFactory(PropertyLazyLoaderFactory propertyLazyLoaderFactory){
        return new DefaultLazyLoadProxyFactory(propertyLazyLoaderFactory);
    }

    @Bean
    public PropertyLazyLoaderFactory propertyLazyLoaderFactory(ApplicationContext applicationContext){
        return new PropertyLazyLoaderFactory(applicationContext);
    }
}

package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutorFactory;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class JoinInMemoryBasedJoinItemExecutorFactoryTest {
    private OrderRepository orderRepository = new OrderRepository();
    private JoinItemExecutorFactory factory;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.geekhalo.lego.core.joininmemory.support");
        applicationContext.refresh();
        BeanResolver beanResolver = new BeanFactoryResolver(applicationContext);
        this.factory = new JoinInMemoryBasedJoinItemExecutorFactory(beanResolver);
    }
    @Test
    void createForType() {
        List<JoinInMemoryTestBean> orders = this.orderRepository.getRandom().stream()
                .map(JoinInMemoryTestBean::new)
                .collect(Collectors.toList());

        List<JoinItemExecutor<JoinInMemoryTestBean>> joinItemExecutors = factory.createForType(JoinInMemoryTestBean.class);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(joinItemExecutors));

        joinItemExecutors.forEach(executor -> executor.execute(orders));

        orders.forEach(order->{
            Assertions.assertNotNull(order.getOrder());
//            Assertions.assertNotNull(order.getUser());
            Assertions.assertNotNull(order.getProduct());
            Assertions.assertNotNull(order.getShop());
        });


    }
}
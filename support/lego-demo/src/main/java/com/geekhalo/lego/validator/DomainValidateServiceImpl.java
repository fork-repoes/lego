package com.geekhalo.lego.validator;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.geekhalo.lego.loader.CreateOrderCmd;
import com.geekhalo.lego.loader.CreateOrderContextV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class DomainValidateServiceImpl
    implements DomainValidateService{
    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ValidateService validateService;


    @Override
    public void createOrder(CreateOrderContext context) {
        validateService.validate(context);
    }

    @Override
    public void createOrder(CreateOrderCmd cmd) {
        CreateOrderContextV2 context = new CreateOrderContextV2();
        context.setCmd(cmd);
        CreateOrderContextV2 contextProxy = this.lazyLoadProxyFactory.createProxyFor(context);
        this.validateService.validate(contextProxy);
    }
}
